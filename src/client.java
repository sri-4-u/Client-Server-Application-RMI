package src;


import java.rmi.*;
import java.io.*;

public class client {
	
public static void main(String[] args) throws RemoteException,NullPointerException,ArrayIndexOutOfBoundsException{
	if(args[0].equals("shutdown"))
	new client(args[0],null,null);
	else if(args[0].equals("download") || args[0].equals("upload")) 
		new client(args[0],args[1],args[2]);
	else new client(args[0],args[1],null);
	
}
public client(String part1,String part2,String part3) {
	
	try {
		String hostID=System.getenv("PA2_SERVER");
		String[]hostAddr=hostID.split(":");
		String host=hostAddr[0];
		ObjectInterface server=(ObjectInterface) Naming.lookup("rmi://"+host+"/RemoteServer");
			if(part1.equals("mkdir"))
				System.out.println(server.Make_Directory(part2));
			else if(part1.equals("rmdir"))
				System.out.println(server.Remove_Directory(part2));
			else if(part1.equals("rm"))
				System.out.println(server.Remove_File(part2));
			else if(part1.equals("dir"))
				System.out.println(server.List_Files(part2));
			else if(part1.equals("shutdown"))
				System.out.println(server.Shut_Down());
			else if(part1.equals("download")) {
				
				String destination=part3;
				
				File file=new File(destination);
				
				BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(file));
				int bytesToDownload=server.get_file_size(part2);
				System.out.println("Bytes to Download : "+bytesToDownload);
				int bytesDownloaded=0;
				if(bytesToDownload>0) {
					
				
				while( bytesDownloaded<bytesToDownload) {
					byte[] buffer1=new byte[1024];
					buffer1=server.Download_Files(part2,bytesDownloaded);
					bytesDownloaded+=buffer1.length;
					bos.write(buffer1);
					bos.flush();
					float percentage= (float)bytesDownloaded/(float)bytesToDownload*(float)100;
					System.out.print("\r");
					System.out.print(((int)percentage)+"%"+" downloaded");
					
				}
				
				
			}
				
				else System.out.println("No data to write");
				bos.close();
			}
			else if(part1.equals("upload")) {
				
				File file =new File(part2);
				
				BufferedInputStream bis=new BufferedInputStream(new FileInputStream(file));
				int bytesToUpload=server.get_file_size(part2);
				//int bytesToUpload=(int) file.length();
				System.out.println("Bytes to Upload : "+bytesToUpload);
				int bytesUploaded=0;
				
				if(bytesToUpload>0) {
				boolean status=false;
				while(bytesUploaded<bytesToUpload) {
					byte[] buffer=new byte[1024];
					//bis.skip(bytesUploaded);
					bytesUploaded+=bis.read(buffer,0,buffer.length);
					server.Upload_Files(part3,buffer,status);
					
					
					status=true;
					float percentage= (float)bytesUploaded/(float)bytesToUpload*(float)100;
					System.out.print("\r");
					System.out.print(((int)percentage)+"%"+" uploaded");
				}
				bis.close();
			}
				else System.out.println("No Data to Read");
			}
				
			else System.out.println("Command Not Found");
	}
	
	catch(java.io.IOException | NotBoundException e) {
		
	}	
			
	
	
	



}
}
