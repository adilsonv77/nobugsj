package pt.uc.dei.nobugssnackbar.suporte;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.digester.Digester;

/**
 * 
 * @author Adilson Vahldick
 *
 */
public class ExercicioFactory
{
    public static Exercicio create(String nomeArquivo, Class<?> classExercicio) throws Exception
    {
        Exercicio exercicio = create(nomeArquivo);
        if (classExercicio != null) {
        	exercicio.setClassExercicio(classExercicio.getName());
        }
        return exercicio;
    }

    public static Exercicio create(String nomeArquivo) throws Exception
    {
        Exercicio exercicio = null;
        Digester d = new Digester();
        exercicio = new Exercicio(nomeArquivo);
        d.push(exercicio);
        
        d.addSetProperties("mission");
        d.addBeanPropertySetter("*/explanation", "explanation");
        d.addBeanPropertySetter("*/cooker", "cooker");
        d.addBeanPropertySetter("*/tests", "tests");
        
        addRuleTestVars(d);
        
        addRuleCustomer(d);
        d.addSetNext("*/customers/customer", "addCustomer");
        
        addRuleObjectives(d);
        d.addSetNext("*/objectives/objective", "addObjective");
        
        ByteArrayInputStream srcfile = new ByteArrayInputStream(decrypt(nomeArquivo));
        d.parse(srcfile);
        exercicio.finalizar();
        return exercicio;
    }
    
	private static byte[] decrypt(String fileName) throws Exception {
		File f = new File(fileName);
        byte[] arq = new byte[(int)f.length()];
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
		in.read(arq);
		in.close();

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] key = "Y&5v4a9!x#/h3,W?".getBytes();

        // Decriptando...
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
        byte[] decrypted = cipher.doFinal(arq);		
        
        return decrypted;
    }
	
	private static void addRuleTestVars(Digester d) {
		
		d.addObjectCreate("*/testsvars/test", HashMap.class);
    	d.addCallMethod("*/testsvars/test/var", "put", 2);
    	d.addCallParam( "*/testsvars/test/var", 0, "name" );
    	d.addCallParam( "*/testsvars/test/var", 1 );
    	
    	d.addSetNext("*/testsvars/test", "addTest");
    	
	}

    private static void addRuleCustomer(Digester d) {

    	d.addObjectCreate("*/customers/customer", CustomerDefinition.class);
    	d.addBeanPropertySetter("*/customers/customer/id");
    	d.addBeanPropertySetter("*/customers/customer/init");
    	d.addBeanPropertySetter("*/customers/customer/dest");
    	
    	d.addObjectCreate("*/customers/customer/orders/order", OrderConf.class);
    	d.addSetNext("*/customers/customer/orders/order", "addOrder");
    	
    	addOrderItem(d, "/foods/food", "addFood");
       	addOrderItem(d, "/drinks/drink", "addDrink");
	}

    private static void addOrderItem(Digester d, String pattern, String methodAdd) {
    	pattern = "*/customers/customer/orders/order" + pattern;
    	d.addObjectCreate(pattern, OrderItem.class);
    	
    	d.addCallMethod(pattern, "setQtdade", 1, new Class[]{int.class});
    	d.addCallParam(pattern, 0, "qt");
    	
    	d.addCallMethod(pattern, "setPrice", 1, new Class[]{int.class});
    	d.addCallParam(pattern, 0, "price");
  
    	d.addBeanPropertySetter(pattern, "type");
    	
    	d.addSetNext(pattern, methodAdd);

    }
    
	private static void addRuleObjectives(Digester d) {
		
		d.addObjectCreate("*/objectives/objective", ObjectiveConf.class);	
		d.addSetProperties("*/objectives/objective");
		d.addBeanPropertySetter("*/objectives/objective", "type");
	}

}