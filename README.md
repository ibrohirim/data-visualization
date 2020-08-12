# Data Visualization in Java
Projects to showcase different ways to visualize data and making it interactive using java.

To run any of the projects run this command in your terminal:

```java -cp <path-of-Main-file> Main```
  
this should compile the app and run it. 

---------------
The "Sorting" project will take as an input a text file containing unsorted numbers then visualize a sorting algorithm using multi threading to divide the sorting and visuzalization actions and improve performace.

The file format is a simple text file with the numbers seprated by a new line "\n" character:
10
2
3
etc..

![VisualizaeSort](https://user-images.githubusercontent.com/46801434/89960576-4424ff80-dbf4-11ea-90dc-b745f6281838.gif)


---------------

The "Tree Map" project analyzes a file system with a selected directory as the root node. Options to visualize by file age, type or permissions.
Hovering over a node will display the files path and size.


![TreeMap](https://user-images.githubusercontent.com/46801434/89962560-e693b180-dbf9-11ea-8f6c-5095b6115a68.gif)

---------------

The "Parallel Coordinates" project visualizaes a large quantinty of data using the parallel lines method. Caveat of this approach is that large amounts of 
data pose a performance impact. Further improvements might be necessary to handle that type of data. 
