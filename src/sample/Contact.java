package sample;

public class Contact implements Comparable<Contact>
{
    String name;
    String email;
    int number;

    public Contact(String name, String email, int number)
    {
        this.name = name;
        this.email = email;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString()
    {
        String output = name;
        if(email != "")
            output += " @";
        if(number != 0)
            output += " #";
        return output;
    }

    @Override
    public int compareTo(Contact contact)
    {
        int posOpenBracketThis = name.lastIndexOf("(");
        int posCloseBracketThis = name.lastIndexOf(")");
        int posOpenBracketOther = contact.name.lastIndexOf("(");
        int posCloseBracketOther = contact.name.lastIndexOf(")");
        String nameThis = name;
        String nameOther = contact.name;
        int indexCopyThis = 0, indexCopyOther = 0;

        if( posOpenBracketThis >= 0)
        {
            nameThis = name.substring(0, posOpenBracketThis);
            indexCopyThis = Integer.parseInt(name.substring(posOpenBracketThis + 1, posCloseBracketThis));
        }
        if( posOpenBracketOther >= 0)
        {
            nameOther = contact.name.substring(0, posOpenBracketOther - 1);
            indexCopyOther = Integer.parseInt(contact.name.substring(posOpenBracketOther + 1, posCloseBracketOther));
        }

        int difference = nameThis.toUpperCase().compareTo(nameOther.toUpperCase());
        if (difference != 0)
            return difference;
        return Integer.compare(indexCopyThis, indexCopyOther);
    }
}
