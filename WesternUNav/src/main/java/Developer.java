/**
 * This class represents a developer who is an extension of a user with more capabilities
 * @author orenj
 */
public class Developer extends User {
    
    /**
     * This is the constructor for the developer which calls the user as a super
     * @param systemID represents the usernames
     * @param password  represents the user's password
     */
    public Developer(String systemID, String password) {
        super(systemID, password);
    }
    
    /**
     * This method adds a poi as a developer 
     * @param layer represents a layer on the map
     * @param id represents the poi id
     * @param roomNum represents the room number
     * @param name represents the poi name
     * @param coordinate represents the coordinates on the map
     * @return the new POI created
     */
    public POI addPOI(String layer, int id, int roomNum, String name, int[] coordinate) {
        POI newPOI = new POI(layer, roomNum, name, coordinate, 1, false);
        return newPOI;
    }
    
}
