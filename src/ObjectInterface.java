package src;

import java.rmi.*;
public interface ObjectInterface extends Remote{
	public String Make_Directory(String filename) throws RemoteException;
	public String Remove_Directory(String filename) throws RemoteException;
	public String Remove_File(String filename) throws RemoteException;
	public String List_Files(String filename) throws RemoteException;
	public byte[] Download_Files(String filename,int index) throws RemoteException;
	public boolean Upload_Files(String filename,byte[]buffer,boolean status) throws RemoteException;
	public String Shut_Down() throws RemoteException;
	public int get_file_size(String filename) throws RemoteException;
	
}
