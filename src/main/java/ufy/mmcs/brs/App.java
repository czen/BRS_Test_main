package ufy.mmcs.brs;

import java.io.IOException;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" +System.getenv("Driver_Path"));

        try{
            Properties env = new Properties();
            env.load(Runtime.getRuntime().exec("cmd.exe /c set").getInputStream());
            String myEnvVar = (String)env.get("Driver_Path");
            System.out.println(myEnvVar);
        }
        catch(IOException bomErr){
            System.out.println(bomErr);
        }
    }

}
