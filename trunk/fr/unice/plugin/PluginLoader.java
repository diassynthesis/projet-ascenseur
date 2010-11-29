package fr.unice.plugin;

import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import java.io.File;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.JarURLConnection;

import java.util.logging.*;
import java.io.*;
import java.util.jar.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.net.MalformedURLException;

/**
 * Charge des classes de plugins dont les fichiers classe sont placs
 * dans des URL donns, et cre une instance de chaque plugin charg.
 * Cette classe dlgue  des ClassLoader le chargement des classes avant de
 * crer les instances.
 * <P>
 * On peut parcourir les URL pour charger " chaud" de nouveaux plugins
 * qui y auraient t nouvellement installs (mthode loadPlugins).
 * En ce cas, les anciens plugins ne sont pas rechargs.
 * On peut mme rcuprer de nouvelles versions des plugins avec
 * les mthodes reloadPlugins.
 * <P>
 * Normalement cette classe est utilise par un PluginManager mais pas
 * directement par les clients qui veulent charger des plugins.
 * @author Richard Grin
 * @version 1.0
 */

public class PluginLoader {
  /**
   * Le chargeur de classes qui va charger les plugins.
   */
  private ClassLoader loader;
  /**
   * Le rpertoire o les plugins seront recherchs.
   */
  private String pluginDirectory;
  /**
   * Liste des instances des plugins qui ont t charges par loadPlugins.
   */
  private List<Plugin> loadedPluginInstances = new ArrayList<Plugin>();

  private static Logger logger =
      Logger.getLogger("fr.unice.plugin.PluginLoader");

  /**
   * Cree une instance qui va chercher les plugins dans le repertoire dont
   * on passe le nom en paramtre.
   * @param directory le repertoire
   */
  public PluginLoader(String directory) throws MalformedURLException {
    // On vrifie que l'URL correspond bien  un rpertoire.
    File dirFile = new File(directory);
    if (dirFile == null || ! dirFile.isDirectory()) {
      logger.warning(directory + " n'est pas un rpertoire");
      throw new IllegalArgumentException(directory + " n'est pas un rpertoire");
    }

    // Si c'est un rpertoire mais que l'URL ne se termine pas par un "/",
    // on ajoute un "/"  la fin (car URL ClassLoader oblige  donner
    // un URL qui se termine par un "/" pour les rpertoires).
    if (!directory.endsWith("/")) {
      directory += "/";
    }
    this.pluginDirectory = directory;
    // Cre le chargeur de classes.
    createNewClassLoader();
  }

  /**
   * Charge les instances des plugins d'un certain type placs dans le
   * rpertoire indiqu  la cration du PluginLoader. Ces instances
   * sont charges en plus de celles qui ont dj t charges.
   * Si on ne veut que les instances des plugins qui vont tre charges
   * dans cette mthode, et avec les nouvelles versions s'il y en a, il faut
   * utiliser {@link #reloadPlugins(Class)}
   * On peut rcuprer ces plugins par la mthode {@link #getPluginInstances}.
   * Si un plugin a dj t charg, il n'est pas
   * recharg, mme si une nouvelle version est rencontre.
   * @param type type des plugins recherchs. Si null, charge les plugins
   * de tous les types.
   */
  public void loadPlugins(Class type) {
    // En prvision d'un chargement ailleurs que d'un rpertoire, on fait
    // cette indirection. On pourrait ainsi charger d'un jar.
    loadFromDirectory(type);
  }

  /**
   * Charge les instances de tous les plugins.
   * On peut rcuprer ces plugins par la mthode {@link #getPluginInstances}.
   * Si un plugin a dj t charg, il n'est pas
   * recharg, mme si une nouvelle version est rencontre.
   */
  public void loadPlugins() {
    loadPlugins(null);
  }

  /**
   * Recharge tous les plugins.
   * Charge les nouvelles versions des plugins s'il les rencontre.
   */
  public void reloadPlugins() {
    reloadPlugins(null);
  }

  /**
   * Recharge tous les plugins d'un type donn.
   * Charge  les nouvelles versions des plugins s'il les rencontre.
   * @param type type des plugins  charger.
   */
  public void reloadPlugins(Class type) {
    // Cre un nouveau chargeur pour charger les nouvelles versions.
    try {
      createNewClassLoader();
    }
    catch(MalformedURLException ex) {
      // Ne devrait jamais arriver car si l'URL tait mal forme,
      // on n'aurait pu crer "this".
      ex.printStackTrace();
    }
    // Et efface tous les plugins du type dj chargs.
    erasePluginInstances(type);
    // Recharge les plugins du type
    loadPlugins(type);
  }

  /**
   * Renvoie les instances de plugins qui ont été récupérés cette fois-ci et
   * les fois d'avant (si on n'a pas effacé les plugins chargés avant lors de la
   * dernire recherche de plugins 
   * @link #loadPlugins(boolean)
   * @return les instances recuperes. Le tableau est plein.
   */
  public Plugin[] getPluginInstances() {
    return getPluginInstances(null);
  }

  public Plugin[] getPluginInstances(Class type) {
    List<Plugin> loadedPluginInstancesOfThatType = new ArrayList<Plugin>();
    for (Plugin plugin : loadedPluginInstances) {
      if (type == null || plugin.getType().equals(type)) {
        loadedPluginInstancesOfThatType.add(plugin);
      }
    }
    return loadedPluginInstancesOfThatType.toArray(new Plugin[0]);
  }

  /**
   * Efface le chargeur de classes.
   */
  private void eraseClassLoader() {
    loader = null;
  }

  /**
   * Cre un nouveau chargeur. Permettra ensuite de charger de nouvelles
   * versions des plugins.
   */
  private void createNewClassLoader() throws MalformedURLException {
    logger.info("******Cration d'un nouveau chargeur de classes");
    loader = URLClassLoader.newInstance(new URL[] { getURL(pluginDirectory) });
  }

  /**
   * Efface tous le plugins d'un certain type dj chargs.
   * @param type type des plugins  effacer. Efface tous les plugins si null.
   */
  private void erasePluginInstances(Class type) {
    if (type == null) {
      loadedPluginInstances.clear();
    }
    else {
      for (Plugin plugin : loadedPluginInstances) {
        if (plugin.getType().equals(type)) {
          loadedPluginInstances.remove(plugin);
        }
      }
    }
  }

  /**
   * Charge les plugins d'un certain type placs dans un rpertoire
   * qui n'est pas dans un jar.
   * @param urlBase URL du rpertoire de base ; la classe du plugin doit
   * se trouver sous ce rpertoire dans un sous-rpertoire qui correspond
   * au nom de son paquetage.
   * Exemple d'URL : file:rep/fichier
   * @param type type des plugins. Charge tous les plugins si <code>null</code>.
   * @param cl le chargeur de classes qui va charger le plugin.
   */
  private void loadFromDirectory(Class type) {
    // Pour trouver le nom complet des plugins trouvs : c'est la partie
    // du chemin qui est en plus du rpertoire de base donn au loader.
    // Par exemple, si le chemin de base est rep1/rep2, le plugin
    // de nom machin.truc.P1 sera dans rep1/rep2/machin/truc/P1.class
    logger.info("=+=+=+=+=+ Entre dans loadPluginsFromDirectory=+=+=+=+");
    loadFromSubdirectory(new File(pluginDirectory), type, pluginDirectory);
    logger.info("=+=+=+=+=+ Sortie de loadPluginsFromDirectory=+=+=+=+");
  }

  /**
   * Charge les plugins placs directement sous un sous-rpertoire
   * d'un rpertoire de base. Les 2 rpertoires ne sont pas dans un jar.
   * @param baseName nom du rpertoire de base (sert pour avoir le nom
   * du paquetage des plugins trouvs).
   * @param dir sous-rpertoire. Le nom du paquetage du plugin devra
   * correspondre  la position relative du sous-rpertoire par rapport
   * au rpertoire de base.
   * @param type type de plugins  charger.
   * @param urlBase URL de base du chargeur de classes (pour savoir d'o
   * viennent les instances de plugins trouves ;  voir si on ne peut pas se
   * passer de ce paramtre...).
   * Charge tous les plugins si <code>null</code>.
   */
  private void loadFromSubdirectory(File dir, Class type,
                                    String baseName) {
    logger.info("Chargement dans le sous-rpertoire " + dir
                + " avec nom de base " + baseName);
    int baseNameLength = baseName.length();
    // On parcourt toute l'arborescence  la recherche de classes
    // qui pourraient tre des plugins.
    // Quand on l'a trouv, on en dduit son paquetage avec sa position
    // relativement  l'URL de recherche.
    File[] files = dir.listFiles();
    logger.info("Le listing : " + files);
    // On trie pour que les plugins apparaissent dans le menu
    // par ordre alphabtique
    //    Arrays.sort(list);
    for (int i = 0; i < files.length; i++) {
      File file = files[i];
      if (file.isDirectory()) {
        loadFromSubdirectory(file, type, baseName);
        continue;
      }
      // Ca n'est pas un rpertoire
      logger.info("Examen du fichier " + file.getPath() + ";" + file.getName());
      String path = file.getPath();
      String qualifiedClassName = getQualifiedName(baseNameLength, path);
      // On obtient une instance de cette classe
      if (qualifiedClassName != null) {
        Plugin plugin = getInstance(qualifiedClassName, type);
        if(plugin != null) {
          logger.info("Classe " + qualifiedClassName + " est bien un plugin !");
          // S'il n'y a pas dj un plugin de la mme classe, on ajoute
          // l'instance de plugin que l'on vient de crer.
          boolean alreadyLoaded = false;
          for (Plugin loadedPlugin : loadedPluginInstances) {
            if (loadedPlugin.getClass().equals(plugin.getClass())) {
              alreadyLoaded = true;
              break;
            }
          }
          if (! alreadyLoaded) {
            loadedPluginInstances.add(plugin);
          }
          logger.info("Les plugins : " + loadedPluginInstances);
        }
      }
    } // for
  }

  /**
   * Dans le cas o un chemin correspond  un fichier .class,
   * calcule le nom complet d'une classe  partir du nom d'un rpertoire
   * de base et du chemin de la classe, les 2 chemins tant ancr au mme
   * rpertoire racine.
   * Le rpertoire de base se termine par "/" (voir classe URLClassLoader).
   * Par exemple, a/b/c/ (c'est--dire 6 pour baseNameLength)
   * et a/b/c/d/e/F.class donneront d.e.F
   * @param baseNameLength nombre de caractres du nom du rpertoire de base.
   * @param classPath chemin de la classe.
   * @return le nom complet de la classe, ou null si le nom ne correspond
   * pas  une classe externe.
   */
  private String getQualifiedName(int baseNameLength, String classPath) {
    logger.info("Calcul du nom qualifi de " + classPath + " en enlevant "
                + baseNameLength + " caractres au dbut");
    // Un plugin ne peut tre une classe interne
    if ((! classPath.endsWith(".class")) || (classPath.indexOf('$') != -1)) {
      return null;
    }
    // C'est bien une classe externe
    classPath = classPath.substring(baseNameLength)
              .replace(File.separatorChar, '.');
    // On enlve le .class final pour avoir le nom de la classe
    logger.info("Nom complet de la classe : " + classPath);
    return classPath.substring(0, classPath.lastIndexOf('.'));
  }

  /**
   * Transforme le nom du rpertoire en URL si le client n'a pas donn
   * un bon format pour l'URL (pour pouvoir crer un URLClassLoader).
   * @param dir nom du rpertoire.
   * @return l'URL avec le bon format.
   * @throws MalformedURLException lanc si on ne peut deviner de quel URL il
   * s'agit.
   */
  private static URL getURL(String dir) throws MalformedURLException {
    logger.info("URL non transforme : " + dir);

  /* On commence par transformer les noms absolus de Windows en URL ;
    * par exemple, transformer C:\rep\machin en file:/C:/rep/machin
   */
    if (dir.indexOf("\\") != -1) {
      // on peut souponner un nom Windows !
      // 4 \ pour obtenir \\ pour l'expression rgulire !
      dir = dir.replaceAll("\\\\", "/");
    } // Nom Windows

    /* C'est un rpertoire ; plusieurs cas :
     *   1. S'il y a le protocole "file:", on ne fait rien ; par exemple,
     *      l'utilisateur indique que les plugins sont dans un rpertoire
     *      avec un nom absolu et, dans ce cas, il doit mettre le protocole
     *      "file:" au dbut ;
     *   2. S'il n'y a pas de protocole "file:", on le rajoute.
     */
    if (! dir.startsWith("file:")) {
      /* On considre que c'est le nom d'une ressource ; si le nom est
       * absolu, c'est un nom par rapport au classpath.
       */
      dir = "file:" + dir;
    }

    logger.info("URL transforme : " + dir);
    return new URL(dir);
  }

  /**
   * Retourne une instance de plugin d'un type donn.
   * @param nomClasse Nom de la classe du plugin
   * @param type type de plugin
   * @param cl chargeur de classes qui va faire le chargement de la classe
   * de plugin
   * @return une instance de plugin. Retourne null si problme.
   * Par exemple, si le plugin n'a pas le bon type.
   */
  private Plugin getInstance(String nomClasse, Class type) {
    logger.info("Entre dans getInstance");
    try {
      // C'est ici que se passe le chargement de la classe par le
      // chargeur de classes.
      logger.info("Demande de chargement de la classe " + nomClasse + " par " + this);

      // ***** DU CODE A ECRIRE ICI !!!!!
      Class newClass = loader.loadClass(nomClasse);
      //    ***** DU CODE A ECRIRE ICI !!!!!
      
      Plugin result = null;
      
      int modifiers = newClass.getModifiers();
      if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers))
      {
    	  return null;
      }

      try {
        // On cre une instance de la classe
        logger.info("Cration d'une instance de " + newClass);

      // ***** DU CODE A ECRIRE ICI !!!!!
        result = (Plugin) newClass.newInstance();
        //      ***** DU CODE A ECRIRE ICI !!!!!

      }
      catch (ClassCastException e) {
        e.printStackTrace();
        //Le fichier  n'est pas un plugin 'Plugin'
        logger.warning("La classe " + nomClasse +
                       " n'est pas un Plugin");
        return null;
      }
      catch (InstantiationException e ) {
        e.printStackTrace();
        logger.warning("La classe " + nomClasse +
                       " ne peut pas etre instanci");
        return null;
      }
      catch (IllegalAccessException e ) {
        e.printStackTrace();
        logger.warning("La classe " + nomClasse +
                       " est interdite d'accs");
        return null;
      }
      catch (NoClassDefFoundError e ) {
        // Survient si la classe n'a pas le bon nom
        e.printStackTrace();
        logger.warning("La classe " + nomClasse +
                       " ne peut tre trouve");
        return null;
      } // Fin des catchs pour le try pour cration instance

      // Teste si plug est du bon type

      // ***** DU CODE A ECRIRE ICI !!!!!

    // }
      if (type != null)
      {
    	  if (type.isInstance(result)) return result;
          else {
        	  logger.info("Plugin de nom " + result.getName()
                    + " n'est pas du bon type " + type.getName());
        	  return null;
          		}
    	}
      else return result;
    }
    // Les catchs pour le 1er try pour chargement de la classe
    catch (ClassNotFoundException e) {
      e.printStackTrace();
      logger.warning("Le plugin " + nomClasse + " est introuvable");
      return null;
    }
    catch (NoClassDefFoundError e ) {
      // Survient si la classe n'a pas le bon nom
      e.printStackTrace();
      logger.warning("La classe " + nomClasse +
                     " ne peut tre trouve");
      return null;
    }

  }
}
