import java.io.File;
import java.io.FilenameFilter;

public class fileExtensionFilterEnds implements FilenameFilter{
   private String ext="*";
   public fileExtensionFilterEnds(String ext){
     this.ext = ext;
   }
   public boolean accept(File dir, String name){
     if (name.endsWith(ext))
       return true;
     return false;
   }
}
