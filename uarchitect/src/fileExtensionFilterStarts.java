import java.io.File;
import java.io.FilenameFilter;

public class fileExtensionFilterStarts implements FilenameFilter{
   private String ext="*";
   public fileExtensionFilterStarts(String ext){
     this.ext = ext;
   }
   public boolean accept(File dir, String name){
     if (name.startsWith(ext))
       return true;
     return false;
   }
}
