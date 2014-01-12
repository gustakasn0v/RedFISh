public class FileServerCommand{
	private String command;
	
	private String argument;
	
	private User executor;

	public FileServerCommand(String command, String argument, User executor){
		this.command = command;
		this.argument = argument;
		this.executor = executor;
	}

	public String toString(){
		return this.executor.toString() + " | Comando: " + this.command +
		" | Argumento: "+this.argument;
	}
}