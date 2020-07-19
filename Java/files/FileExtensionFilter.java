/*
 * NOTE:
 * This class is modified from a post made by "Stevo" on 27 February 2015, here:
 * https://stackoverflow.com/questions/28757272/how-to-restrict-a-jfilechooser-to-a-custom-file-type
 * 
 */

package files;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * @author Andrew Thompson
 * 
 * NOTE: This class is modified from a post made by "Stevo" on 27 February 2015, here:
 * https://stackoverflow.com/questions/28757272/how-to-restrict-a-jfilechooser-to-a-custom-file-type
 *
 */
public class FileExtensionFilter extends FileFilter {
	private String allowedExtension = "";
	private String allowedExtensionDescription = "";
	
	/**
	 * Returns a new FileExtensionFilter object
	 * @param allowedExtension	String representation of allowable file extension, e.g. "txt"
	 * @param allowedExtensionDescription	English language description of allowedExtension, e.g. "Text Document"
	 */
	public FileExtensionFilter(String allowedExtension, String allowedExtensionDescription) {
		this.allowedExtension = allowedExtension;
		this.allowedExtensionDescription = allowedExtensionDescription;
	}

	@Override
	public boolean accept(File f) {
		if(f.isDirectory()) {return true;}  //Allows folders to be browsed into, to find a file.  
										   //Does not allow a folder to be "loaded". 
		
		String selectedFileExtension = getExtension(f);
		if(selectedFileExtension==null || selectedFileExtension.equals("")) {return false;} //The file must have an extension
		
		if(!selectedFileExtension.equals(allowedExtension)) {return false;} //Only the specified file extension is allowed
		
		return true;
	}

	@Override
	public String getDescription() {
		return(allowedExtensionDescription + " (." + allowedExtension + ")");
	}
	
	
	public String getExtension(File f)
    {
        String extension = null;
        String fileName = f.getName();

        int divide = fileName.lastIndexOf('.');

        if(divide > 0 && divide < fileName.length() - 1) {
        		extension = fileName.substring(divide + 1);
        		extension = extension.toLowerCase();
        }
        return extension;
    }

}
