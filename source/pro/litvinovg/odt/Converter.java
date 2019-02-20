package pro.litvinovg.odt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Converter {

private File inputFile = null;
private ODTFile odtFile = null;

static final String SUFFIX = "_save.odt";

	public static void main(String args[]) throws Exception{
		Converter converter = new Converter();
		converter.ConvertFootNotesToEndNotes(args);
	}
	
	public void ConvertEndNotesToFootNotes(String args[]) {
		configure(args);
		odtFile.replaceEndNotesToFootNotes();
	}

	public void ConvertFootNotesToEndNotes(String args[]) {
		configure(args);
		odtFile.replaceFootNotesToEndNotes();
	}
	
	private void configure(String[] args) {
		if (args.length < 1) {
			System.err.println("Usage: java -jar ODTNoteConverter.jar [input_file.odt]");
			}
		// Set input file
		  if (args.length > 0){
    		String inputFileName = args[0];
      	inputFile = new File(inputFileName);
    	} else {
    		inputFile = FileChooser.chooseFile();
    	}
		  //checkFile(inputFile);
		// Set output file
		  if (args.length == 0){
		  	Path inputFilePath = Paths.get(inputFile.getAbsolutePath());
		  	Path outputFilePath = Paths.get(inputFile.getAbsolutePath()+ SUFFIX);
 	
		  	try {
					Files.copy(inputFilePath,outputFilePath , StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					System.out.println("Input Output Error");
					e.printStackTrace();
				}
 	   	} 
		  odtFile = new ODTFile(inputFile);
	}
	
	private void checkFile(File file) {
	  if (file == null || !file.exists()){
  		System.err.println("File " + file.getAbsolutePath() + " not found!");
  		System.exit(1);
  	}		
  	if (!file.canRead()) {
  		System.err.println("File " + file.getAbsolutePath() + " can't be read!");
  		System.exit(1);
  	}
		
	}
}
