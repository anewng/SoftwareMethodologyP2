package bank;

import java.util.Locale;

/**
 The Profile class is used to create/manipulate/access Patient objects.
 @author Annie Wang, Jasmine Flanders
 */
public class Profile {
    private String fname;
    private String lname;
    private Date dob;

    /**
     Constructor takes first, last, birth and creates a Profile object.
     @param first the first name of the Patient
     @param last the last name of the Patient
     @param birth the birth date of the Patient
     */
    public Profile(String first, String last, Date birth){
        fname = first;
        lname = last;
        dob = birth;
    }

    /**
     Gets the first name
     @return fname is the first name of the Profile
     */
    public String getFname(){
        return fname;
    }

    /**
     Gets the last name
     @return lname is the last name of the Profile
     */
    public String getLname(){
        return lname;
    }

    /**
     Gets the date of birth
     @return dob is the dob of the Profile
     */
    public Date getDob(){
        return dob;
    }

    /**
     Returns ints corresponding to the relationship between the two Profiles being compared
     @param person the profile being compared to
     @return 0 if profile are the same, -1 if profile are not equal
     */
    public int equals(Profile person){
        String thisFName = this.fname.toLowerCase(), thisLName = this.lname.toLowerCase(),
                fName = person.fname.toLowerCase(), lName = person.lname.toLowerCase();
        if( (thisFName.compareTo(fName) == 0) && ((thisLName.compareTo(lName)) == 0)
            && (this.dob.compareTo(person.dob) == 0) ){
            return 0;
        }else{
            return -1;
        }
    }

    /**
     Converts the Profile into a string
     @return String of the Patient object
     */
    public String toString(){
        String returnString = fname + " " + lname + " " + dob.toString();
        return returnString;
    }
}
