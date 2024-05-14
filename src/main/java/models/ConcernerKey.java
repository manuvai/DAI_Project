package models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class ConcernerKey implements Serializable {
	
	/**
	 * id du Post-it 
	 */
    @Column(name = "IdPostIt")
    private int idPostIt;
    
    /**
     * id de la liste de course
     */
    @Column(name = "IdListeDeCourse")
    private int idListeDeCourse;
    
	/**
	 * Constructeur avec attributs
	 * @param idPostIt
	 * @param idListeDeCourse
	 */
    public ConcernerKey(int idPostIt, int idListeDeCourse) {
        setIdPostIt(idPostIt);
        setIdListeDeCourse(idListeDeCourse);
    }

    /**
     * get post-it Id
     * @return id du post-it
     */
    public int getIdPostIt() {
        return idPostIt;
    }

    /**
     * set post-it Id
     * @param idPostIt
     */
    public void setIdPostIt(int idPostIt) {
        this.idPostIt = idPostIt;
    }

    /**
     * get liste de course id
     * @return id de la liste de course
     */
    public int getIdListeDeCourse() {
        return idListeDeCourse;
    }

    /**
     * set liste de course Id
     * @param idListeDeCourse
     */
    public void setIdListeDeCourse(int idListeDeCourse) {
        this.idListeDeCourse = idListeDeCourse;
    }

    /**
     * compare current ConcernerKey with another Object
     * @return true if it's the same object or if the post-it and liste de course have the same id. false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConcernerKey)) return false;
        ConcernerKey that = (ConcernerKey) o;
        return getIdPostIt() == that.getIdPostIt()
                && getIdListeDeCourse() == that.getIdListeDeCourse();
    }

    /**
     * creates a hash with ids of liste de course and post-it
     * @return hash of idListeDeCourse and idPostIt
     */
    @Override
    public int hashCode() {
        return Objects.hash(getIdPostIt(), getIdListeDeCourse());
    }
    
	/**
	 * Constructeur sans attribut
	 */
    public ConcernerKey() {}
}
