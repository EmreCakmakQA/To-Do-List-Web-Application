# To-Do-List-Web-Application

Test Coverage: 89.6%

The purpose of this project was to utilise Back-End and Front-End technologies to create a todo list application. This encapsulates:

*Agile & Project Management
*Databases & Cloud Fundamentals
*Programming Fundamentals
*Front-End Web Technologies
*API Development
*Automated Testing

## Getting Started

Follow the instructions below to get up and running on your machine using a locally hosted instance.


## Prerequisites

The software you will require are as follows:

*Maven
*Spring Tool Suite
*JDK Version 11
*VSCode 1.5 and above (also consider downloading live server extension)


## Running the application from the .jar

Download the .jar file in the documentation folder. Once downloaded open your command line and type the following:

```
java -jar todoProject-0.0.1-SNAPSHOT.jar

```

## Cloning the repo and running via Spring Tool Suite

Clone the repo with the following:

```
git clone https://github.com/EmreCakmakQA/To-Do-List-Web-Application.git

```

Once that is cloned, open the project in Spring Tool Suite and run it locally. To do this, click on "local" on bottom left corner of Spring Tool Suite, followed by "todoProject". This runs the project locally on localhost:9092.

Alternatively, right-click the project folder and choose "Run As", followed by "Spring Boot App".


## Testing

###Integration Tests
Used for testing the codebase on a bigger scale. Testing is currently at 89.6%.
An example of the controller integration testing for one of the CRUD methods (Create):

```
	// Create Test
	@Test
	void createTest() throws Exception {
	    TodoDto testDTO = mapToDTO(new Todo("Buy eggs", false));
	    String testDTOAsJSON = this.jsonifier.writeValueAsString(testDTO);

	    RequestBuilder request = post(URI + "/create").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);

	    ResultMatcher checkStatus = status().isCreated();

	    TodoDto testSavedDTO = mapToDTO(new Todo("Buy eggs", false));
	    testSavedDTO.setId(5L);
	    String testSavedDTOAsJSON = this.jsonifier.writeValueAsString(testSavedDTO);

	    ResultMatcher checkBody = content().json(testSavedDTOAsJSON);

	    this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

```
