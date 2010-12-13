package fr.unice.plugin;

/**
 * Interface g�n�rique que doivent impl�menter tous les plugins.
 * Les classes qui impl�mentent cette interface devront aussi fournir
 * un constructeur sans param�tre (d'une fa�on implicite ou non).
 *
 * @author  Richard Grin (modifi� de la version de Michel Buffa)
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
