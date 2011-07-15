package Running;
import java.io.File; 
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;


public class getFile {
	
 
	public static File getFile(String path,String e)
	{
		final String extention;
		extention=e;
		JFileChooser chooser=new JFileChooser(path);
		chooser.setDialogTitle("选择"+extention+"文件");
		chooser.setFileFilter(new FileFilter(){
			@Override
			public boolean accept(File file) {
				// TODO Auto-generated method stub
				if(file.getName().endsWith("."+extention)||file.isDirectory())
					return true;
				return false;
			}
			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return extention+"文件"+"(*."+extention+")";
			}
		});
		int result=chooser.showOpenDialog(null);
		if(!(result==JFileChooser.APPROVE_OPTION))
		{
			System.out.print("cannot open the file\n");
			System.exit(1);
			return null;
		}
		return chooser.getSelectedFile();	
	}
 
}
