#intro
All the code is in SimpleDB.java including the *main* method.
The *SimpleDB* class contains 3 non-static methods:

*   *startDB()* - creates the simple.db file if not existing.
    If the file exists the indexes are loaded into the HashMap variable *indexMap*.
    This method is called from the constructor of *SimpleDB*.
*   *write()* - writes to file and stores the index in the *indexMap*.
*   *read()* - gets position in the file from the *indexMap* and reads the value from the file.

#How to use
Go to /src.
Run below commands (use *sudo* if permisson denied):

    docker build -t assignment1 .
    docker run -ti assignment1