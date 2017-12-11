package bo.data;

import java.util.LinkedList;

import services.Random;

public class Data {
	
	//Tipos de instru��o disponiveis
	public static final String DATA_COMPONENT_INSTRUCTION = "Component Instruction";
	public static final String DATA_INTEGER = "Integer";
	public static final String DATA_ARRAYLIST = "ArrayList";
	public static final String[] dataTypes = {DATA_COMPONENT_INSTRUCTION, DATA_INTEGER, DATA_ARRAYLIST};
	
	//Tipos de componentes disponiveis para a instru��o
	public static final String COMPONENT_INSTRUCTION_DISK = "Disk";
	public static final String[] componentInstructions = {COMPONENT_INSTRUCTION_DISK};
	
	//Identificador de tipo de instru��o
	private String identifier;
	
	//Dado preenchido de acordo com o tipo da instru��o
	private String dataComponentInstruction;
	private int dataInteger;
	private LinkedList<Data> dataList;
	
	public Data(String identifier){
		this.identifier = identifier;
		
		if(this.identifier.equals(DATA_COMPONENT_INSTRUCTION)){
			this.dataComponentInstruction = Random.generateRandomComponentInstruction();
			this.dataInteger = -1;
			this.dataList = null;
		}
		else if(this.identifier.equals(DATA_INTEGER)){
			this.dataInteger = Random.generateRandomInt(0,20);
			this.dataList = null;
			this.dataComponentInstruction = null;
		}
		else if(this.identifier.equals(DATA_ARRAYLIST)){
			this.dataList = Random.generateRandomDataListWithoutLists(Random.generateRandomInt(1,10));
			this.dataInteger = -1;
			this.dataComponentInstruction = null;
		}
	}
	
	//Getters and Setters
	public String getIdentifier(){
		return this.identifier;
	}

	public String getDataComponentInstruction() {
		return this.dataComponentInstruction;
	}

	public int getDataInteger() {
		return this.dataInteger;
	}

	public LinkedList<Data> getDataList() {
		return this.dataList;
	}

	//Metodo toString personalizado para impress�o das instru��es de modo f�cil de se entender no console
	@Override
	public String toString() {
		String response = "";
		
		if(this.identifier.equals(DATA_COMPONENT_INSTRUCTION)){
			response = "Data [Type: " + DATA_COMPONENT_INSTRUCTION + ", Value: " + this.dataComponentInstruction + "]";
		}
		else if(this.identifier.equals(DATA_INTEGER)){
			response = "Data [Type: " + DATA_INTEGER + ", Value: " + this.dataInteger + "]";
		}
		else if(this.identifier.equals(DATA_ARRAYLIST)){
			response = "Data [Type: " + DATA_ARRAYLIST + ", Value: {";
			
			for(Data d: this.dataList){
				response = response + "\n\t" + d;
			}
			
			response = response + "\n}]";
		}
		else{
			response = "Data [Type: undefined, Value: undefined]";
		}
		
		return response;
	}
	
	
	
}
