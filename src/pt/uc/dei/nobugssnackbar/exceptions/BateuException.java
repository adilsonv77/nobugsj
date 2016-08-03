package pt.uc.dei.nobugssnackbar.exceptions;


/**
 * 
 * @author Adilson Vahldick
 *
 */
public class BateuException extends MundoException
{

    public BateuException()
    {
        super("O objeto tentou invadir o espa\347o de outro objeto");
    }
}