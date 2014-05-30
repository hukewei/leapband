package fr.utc.leapband.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
