# World-Map-Shortest-Path
** Important Note: **This project is meant to be run in a 1366x768 screen, using it in any other resolution will make the gui look messy, but it will still run.

### Basic information about the project and GUI

The project's purpose is to find the shortest paths between cities based on the shortest paths between them. The cities, paths and distances can all be found in the Countries.txt and Coutries_Random.txt. Each city is represented as a node and each path between two cities is represented as an edge. Forgive the GUI for being wonky, the purpose of this project was to display the djikstra algorithm.

The GUI contains a simple world map based on the peter's projection version, which displays the sizes proportiantly (provides accurate scaling). The map contains buttons of each city in it's proper location. Each button can be selected to be the source or destination. On the right side, it displays a small menu designed for selecting the source and destination as well as some information about them. When "Compute" is pressed, the path is found using djikstra and displayed on the map and in a text area. The distance is displayed in "coordinate units" and in kilometers.


There are small quality of life additions to the map such as displaying the path on the map, lighting the source and destination locations in different colors when clicked, displaying the names of the country when hovering over them etc as well as some others.


Countries.txt contains a text file of different cities, paths and distances. The inital line is used to define every country with it's real world coordinates. During runtime, the program will find the actual location of the each country on the map provided in the GUI. The paths are also defined as a city pair ( city1,city2 ). The distance of the path though is only found during calculation of the distance between two chosen cities using the two points distance formula. The paths were hand-made designed to be as realistic as possible.

Countries_Random.txt is the same as Countries.txt  except it contains randomly generated paths between the cities.

This project contains a custom made graph data structure with it's accompanying files to make some of it's methods work (like a queue).

### Djikstra portion

Since the instructions prohibited the storing 
