package src;

import java.io.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
public class server extends UnicastRemoteObject implements ObjectInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public server() throws RemoteException{
		
	}
	public static void main(String[] args) {
		try {
			ObjectInterface server=new server();
			Naming.rebind("RemoteServer", server);
			System.out.println("Binding complete...");
		}
		catch(java.io.IOException e) {
			
		}
	}
	public String Make_Directory(String filename) {
		File file1=new File(filename);
		if(!file1.exists()) {
		file1.mkdirs();
		return "Directory "+filename+" is created";
		}
		else if(file1.exists())
			return "Directory "+filename+" already exists";
	
		else return("Directory cannot be created");
	}
	public String Remove_Directory(String filename) {
		File file=new File(filename);
		if(file.exists()) {
			if(file.isDirectory()) {
				if(file.listFiles().length==0) {
					file.delete();
					return "Directory "+filename+" is removed";
							}
				else return "Directory "+filename+" is not empty";
				}
			else return filename+" is not a directory";
			}
		else return "No such directory exists";
			
			
		
	}
	public String Remove_File(String filename) {
		File file=new File(filename);
		if(file.exists()) {
			if(file.isFile()) {
				file.delete();
				return filename+" is removed";
			}
			else return filename+" is not a file";
		}
		else return filename+" does not exists";
			
				
	}
	public String List_Files(String filename) {
		String list="";
		File file=new File(filename);
		if(file.exists()) {
			File [] files=file.listFiles();
			for(int i=0;i<files.length;i++) {
				if(files[i].isFile()) {
					list=list+files[i].getName()+" ";}
					
				else if(files[i].isDirectory()) {
					list=list+files[i].getName()+" ";}
					
					
				}
			return list;
			}
		else return filename+" does not exists";
		}
		
		
	public String Shut_Down() {
		try {
			UnicastRemoteObject.unexportObject(this, true);
		} catch (NoSuchObjectException e) {
			
			e.printStackTrace();
		}
		return "Server closed successfully";
	}
	public byte[] Download_Files(String filename,int index) {
		try {
		File file=new File(filename);
		if(!file.exists())
			return null;
		else {
			byte[] buffer=new byte[1024];
			BufferedInputStream bis;
			
				bis = new BufferedInputStream(new FileInputStream(file));
				bis.skip(index);
				bis.read(buffer);
				bis.close();
				return buffer;
				
			} 
		}
			catch (IOException e) {
				
				e.printStackTrace();
				return null;
			}
			
		
		
	}
	
	public boolean Upload_Files(String filename,byte[] buffer,boolean status) throws RemoteException {
		File file=new File(filename);
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file,status));
			bos.write(buffer);
			bos.flush();
			bos.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	public int get_file_size(String filename) throws RemoteException {
		int size=-1;
		File file=new File(filename);
		if(file.exists())
			 size=(int) file.length();
				return size;
		
	}
	
}
