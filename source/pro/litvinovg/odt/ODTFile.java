package pro.litvinovg.odt;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.*;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.io.IOUtils;


public class ODTFile {
	
	private final String CONTENT = "content.xml";
	private final String STYLES = "styles.xml";
	private FileSystem zipfs;
	private Path contentPath;
	private Path stylesPath;
	private File file;
	
	public ODTFile(File file) {
		this.file = file;
	}
	private void configure() throws IOException {
		Map<String, String> env = new HashMap<>(); 
    env.put("create", "true");
    Path path = Paths.get(file.getAbsolutePath());
    URI uri = URI.create("jar:" + path.toUri());
    zipfs = FileSystems.newFileSystem(uri, env);
    contentPath = zipfs.getPath(CONTENT);
   // System.out.println(contentPath.toString());
    stylesPath = zipfs.getPath(STYLES);
	}
	
	
	public void replaceEndNotesToFootNotes() {
		 
		 replace(CONTENT,"note-class=\"endnote","note-class=\"footnote");
		 replace(CONTENT,"note-class=\"Endnote","note-class=\"Footnote");
		 
	}
	public void replaceFootNotesToEndNotes(){
		 replace(CONTENT,"note-class=\"footnote","note-class=\"endnote");
		 replace(CONTENT,"note-class=\"Footnote","note-class=\"Endnote");
	}
	 
	private void replace(String fileName, String search, String replace) {
		Path path = null;
		try {
			configure();
			if (fileName.equals(CONTENT)) {
				path = contentPath;
			} else if (fileName.equals(STYLES)) {
				path = stylesPath;
			}
			replaceFileContents(path, search, replace);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				zipfs.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
		private void replaceFileContents(Path path, String search, String replace) {
		BufferedReader reader = null;
		BufferedWriter writer = null;
		StringBuilder stringBuilder = null;
		try {
			reader = Files.newBufferedReader(path,StandardCharsets.UTF_8);
			stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String fileContents;
		try {
			fileContents = stringBuilder.toString();
			fileContents = fileContents.replaceAll(search, replace);
			writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.WRITE);
			writer.write(fileContents);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
					
	}
	
	
}
