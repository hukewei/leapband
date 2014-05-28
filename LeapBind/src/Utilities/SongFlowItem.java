package Utilities;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class SongFlowItem {
	
	    private String label;

	    private File file;

	   /* public SongFlowItem(String fileName, String label)
	    {
	        this(new File(fileName), label);
	    }*/

	    public SongFlowItem(File file, String label)
	    {
	        this.file = file;
	        this.label = label;
	    }

	   

	    public static List<SongFlowItem> loadFromDirectory(File directory)
	    {
	        List<SongFlowItem> list = new ArrayList<SongFlowItem>();

	        if (!directory.isDirectory())
	        {
	            return list;
	        }

	        File[] files = directory.listFiles();

	        for (int index = 0; index < files.length; index++)
	        {
	            SongFlowItem item = new SongFlowItem(files[index], files[index].getName());
	            list.add(item);
	        }

	        return list;
	    }

	  

	  

	    public String getLabel()
	    {
	        return label;
	    }
	    public File getFile(){
	    	return file;
	    }

	

}
