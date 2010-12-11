package fr.unice.plugin;

/**
 * Interface générique que doivent implémenter tous les plugins.
 * Les classes qui implémentent cette interface devront aussi fournir
 * un constructeur sans paramètre (d'une façon implicite ou non).
 *
 * @author  Richard Grin (modifié de la version de Michel Buffa)
 * @version 2.0 7/12/02
 */
public interface Plugin {
  /**
   * @return le nom du plugin
   */
  public String getName();

  /**
   * @return le type du plugin
   */
  public Class getType();
}
