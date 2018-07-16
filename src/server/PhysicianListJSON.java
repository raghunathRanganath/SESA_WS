package server;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PhysicianListJSON {

	public String name;
    public int idNum;
    public String specialty;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIdNum() {
		return idNum;
	}
	public void setIdNum(int idNum) {
		this.idNum = idNum;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

    
}
