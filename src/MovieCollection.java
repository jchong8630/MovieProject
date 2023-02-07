import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;
    private ArrayList<String> cast;
    private ArrayList<String> genre;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
        cast = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            String castMembers[] = movies.get(i).getCast().split("\\|");
            List<String> c = new ArrayList<String>();
            c = Arrays.asList(castMembers);
            for (String n : c){
                if (!(cast.contains(n))){
                    cast.add(n);
                }
            }
        }
        genre = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            String genres[] = movies.get(i).getGenres().split("\\|");
            List<String> g = new ArrayList<String>();
            g = Arrays.asList(genres);
            for (String n : g){
                if (!(genre.contains(n))){
                    genre.add(n);
                }
            }
        }
        Collections.sort(cast);
        Collections.sort(genre);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();


    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        ArrayList<String> results = new ArrayList<>();
        System.out.print("Enter cast member: ");
        String searchTerm = scanner.nextLine();
        for (int i = 0; i < cast.size(); i++){
            if (cast.get(i).toLowerCase().contains(searchTerm.toLowerCase())){
                results.add(cast.get(i));
            }
        }

        for (int i = 0; i < results.size(); i++)
        {
            String castMember = results.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + castMember);
        }

        System.out.print("Choose a cast member: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedCast = results.get(choice - 1);
        ArrayList<Movie> movieResult = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieCast = movies.get(i).getCast();
            movieCast = movieCast.toLowerCase();

            if (movieCast.indexOf(selectedCast.toLowerCase()) != -1)
            {
                //add the Movie objest to the results list
                movieResult.add(movies.get(i));
            }
        }

        for (int i = 0; i < movieResult.size(); i++)
        {
            String title = movieResult.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int movieChoice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = movieResult.get(movieChoice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords(){
    System.out.print("Enter a keyword: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
    {
        String movieKeyWord = movies.get(i).getKeywords();
        movieKeyWord = movieKeyWord.toLowerCase();

        if (movieKeyWord.indexOf(searchTerm) != -1)
        {
            //add the Movie objest to the results list
            results.add(movies.get(i));
        }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
    {
        String title = results.get(i).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;

        System.out.println("" + choiceNum + ". " + title);
    }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

    int choice = scanner.nextInt();
        scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
}

    private void listGenres()
    {
        for (int i = 0; i < genre.size(); i++)
        {
            String g = genre.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + g);
        }
        System.out.print("Choose a genre: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        String genreChoice = genre.get(choice - 1);
        ArrayList<Movie> results = new ArrayList<Movie>();

        for (int i = 0; i < movies.size(); i++)
        {
            String movieGenre = movies.get(i).getGenres();
            movieGenre = movieGenre.toLowerCase();

            if (movieGenre.indexOf(genreChoice.toLowerCase()) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void listHighestRated()
    {
        ArrayList<Movie> results = new ArrayList<Movie>();
        results.add(movies.get(0));
        for (int i = 1; i < movies.size(); i++){
            for (int j = 0; j < results.size(); j++){
                if (movies.get(i).getUserRating() >= results.get(j).getUserRating()){
                    results.add(j, movies.get(i));
                    break;
                }
            }
        }
        for (int i = 0; i < 50; i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title + " " + results.get(i).getUserRating());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> results = new ArrayList<Movie>();
        results.add(movies.get(0));
        for (int i = 1; i < movies.size(); i++){
            for (int j = 0; j < results.size(); j++){
                if (movies.get(i).getRevenue() >= results.get(j).getRevenue()){
                    results.add(j , movies.get(i));
                    break;
                }
                else if (results.size() < 50){
                    results.add(j, movies.get(i));
                    break;
                }
            }
        }
        int choiceNum = 1;
        for (int i = 49; i >= 0; i--)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!

            System.out.println("" + choiceNum + ". " + title + ": " + results.get(i).getRevenue());
            choiceNum++;
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(50 - choice);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}