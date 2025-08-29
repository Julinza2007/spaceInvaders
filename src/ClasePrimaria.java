import GUI.spaceInvaders;
public class ClasePrimaria {

	public static void main(String[] args) {
		mostrarSpaceInvaders();
	}
	
	public static void mostrarSpaceInvaders() {
		spaceInvaders spaceInv = new spaceInvaders();
		spaceInv.setVisible(true);
	}

}
