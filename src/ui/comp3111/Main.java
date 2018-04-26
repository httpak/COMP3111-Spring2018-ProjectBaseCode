package ui.comp3111;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataType;
import core.comp3111.SampleDataGenerator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The Main class of this GUI application
 * 
 * @author cspeter
 *
 */
public class Main extends Application {

	// Attribute: DataTable
	// In this sample application, a single data table is provided
	// You need to extend it to handle multiple data tables
	// Hint: Use java.util.List interface and its implementation classes (e.g.
	// java.util.ArrayList)
	private DataTable sampleDataTable = null;

	// Attributes: Scene and Stage
	private static final int SCENE_NUM = 13;
	private static final int SCENE_MAIN_SCREEN = 0;
	private static final int SCENE_IMPORT_SCREEN = 1;
	private static final int SCENE_EXPORT_SCREEN = 2;
	private static final int SCENE_OPERATION_SCREEN = 3;
	private static final int SCENE_FILTER_NUM_SCREEN = 4;
	private static final int SCENE_FILTER_TEXT_SCREEN = 5;
	private static final int SCENE_LINE_CHART_SELECTION_NUM1_SCREEN = 6;
	private static final int SCENE_LINE_CHART_SELECTION_NUM2_SCREEN = 7;
	private static final int SCENE_PIE_CHART_SELECTION_TEXT_SCREEN = 8;
	private static final int SCENE_PIE_CHART_SELECTION_NUM_SCREEN = 9;
	private static final int SCENE_LINE_CHART_SCREEN = 10;
	private static final int SCENE_PIE_CHART_SCREEN = 11;
	private static final int SCENE_ANIMATED_LINE_CHART_SCREEN = 12;
	private static final String[] SCENE_TITLES = { "COMP3111 Chart - [hiGGWP]", "IMPORT","EXPORT","OPERATION",
			"FILTER NUM","FILTER TEXT","LINE CHART SELECTION NUM1","LINE CHART SELECTION NUM2",
			"PIE CHART SELECTION TEXT","PIE CHART SELECTION NUM","LINE CHART","PIE CHART", "ANIMATED LINE CHART"};
	private Stage stage = null;
	private Scene[] scenes = null;
	// To keep this application more structural,
	// The following UI components are used to keep references after invoking
	// createScene()
	// Screen 0: paneMainScreen
	private Button btImport, btExport, btDataset1, btDataset2, btDataset3, btOperation, btSaveDatasets, btLoadDatasets, btLoadChart;
	private Label lbMainScreenTitle, lbSampleDataTable, lbMainScreenComment;

	// Screen 1: paneImportScreen
	private Button btImportToDataset1,btImportToDataset2,btImportToDataset3;
	// Screen 2: paneEmportScreen
	private Button btExportDataset1,btExportDataset2,btExportDataset3;
	// Screen 3: paneOperationScreen
	private Button btFilterText, btFilterNum, btPlotLineChart, btPlotPieChart, btOperationBackMain;
	private Label lbOperationScreenSampleDataTable, lbOperationScreenTitle, lbFilterData, lbPlotChart;
	
	// Screen 4: paneFilterNumScreen
	
	// Screen 5: paneFilterTextScreen
	
	// Screen 6: paneLineChartSelectionNum1Screen
	private TextField tfLineChartSelectionNum1;
	private Button btLineChartSelectionNum1continue, btLineChartSelectionNum1back;
	private Label lbLineChartSelectionNum1ScreenTitle, lbLineChartSelectionNum1Name, lbLineChartSelectionNum1;
	private String lineChartSelectionNum1input;
		
	// Screen 7: paneLineChartSelectionNum2Screen
	private TextField tfLineChartSelectionNum2, tfLineChartSelectionTitle;
	private Button btLineChartSelectionNum2continue, btLineChartSelectionNum2back;
	private Label lbLineChartSelectionNum2ScreenTitle, lbLineChartSelectionNum2Name, lbLineChartSelectionNum2, 
		lbLineChartSelectionTitle, lbLineChartSelectionTitleName;
	private String lineChartSelectionNum2input, lineChartSelectionTitle;
	
	// Screen 8: panePieChartSelectionTextScreen
	private TextField tfPieChartSelectionText;
	private Button btPieChartSelectionTextcontinue, btPieChartSelectionTextback;
	private Label lbPieChartSelectionTextScreenTitle, lbPieChartSelectionTextName, lbPieChartSelectionText;
	private String pieChartSelectionTextInput;
	
	// Screen 9: panePieChartSelectionNumScreen
	private TextField tfPieChartSelectionNum, tfPieChartSelectionTitle;
	private Button btPieChartSelectionNumcontinue, btPieChartSelectionNumback;
	private Label lbPieChartSelectionNumScreenTitle, lbPieChartSelectionNumName, lbPieChartSelectionNum, 
		lbPieChartSelectionTitle, lbPieChartSelectionTitleName;
	private String pieChartSelectionNumInput, pieChartSelectionTitle;
		
	// Screen 10: paneLineChartScreen
	private LineChart<Number, Number> lineChart = null;
	private NumberAxis lineChartxAxis = null;
	private NumberAxis lineChartyAxis = null;
	private Button btLineChartBackMain, btLineChartAnimated, btLineChartSave;
		
	// Screen 11: panePieChartScreen
	private PieChart pieChart = null;
	private ObservableList<PieChart.Data> pieChartData = null;
	private Button btPieChartBackMain, btPieChartSave;
	
	// Screen 12: animatedLineChartScreen
	private LineChart<Number, Number> animatedLineChart = null;
	private NumberAxis animatedLineChartxAxis = null;
	private NumberAxis animatedLineChartyAxis = null;
	private Button btAnimatedLineChartBackMain;
	private int valuesCount;
	
	/**
	 * create all scenes in this application
	 */
	private void initScenes() {
		scenes = new Scene[SCENE_NUM];
		scenes[SCENE_MAIN_SCREEN] = new Scene(paneMainScreen(), 400, 500);
		scenes[SCENE_IMPORT_SCREEN] = new Scene(paneImportScreen(), 400, 500);
		scenes[SCENE_EXPORT_SCREEN] = new Scene(paneExportScreen(), 400, 500);
		scenes[SCENE_OPERATION_SCREEN] = new Scene(paneOperationScreen(), 400, 500);
		scenes[SCENE_FILTER_NUM_SCREEN] = new Scene(paneFilterNumScreen(), 400, 500);
		scenes[SCENE_FILTER_TEXT_SCREEN] = new Scene(paneFilterTextScreen(), 400, 500);
		scenes[SCENE_LINE_CHART_SELECTION_NUM1_SCREEN] = new Scene(paneLineChartSelectionNum1Screen(), 400, 500);
		scenes[SCENE_LINE_CHART_SELECTION_NUM2_SCREEN] = new Scene(paneLineChartSelectionNum2Screen(), 400, 500);
		scenes[SCENE_PIE_CHART_SELECTION_TEXT_SCREEN] = new Scene(panePieChartSelectionTextScreen(), 400, 500);
		scenes[SCENE_PIE_CHART_SELECTION_NUM_SCREEN] = new Scene(panePieChartSelectionNumScreen(), 400, 500);
		scenes[SCENE_LINE_CHART_SCREEN] = new Scene(paneLineChartScreen(), 800, 600);
		scenes[SCENE_PIE_CHART_SCREEN] = new Scene(panePieChartScreen(), 800, 600);
		scenes[SCENE_ANIMATED_LINE_CHART_SCREEN] = new Scene(paneAnimatedLineChartScreen(), 800, 600);
		
		for (Scene s : scenes) {
			if (s != null)
				//Assumption: all scenes share the same stylesheet
				s.getStylesheets().add("Main.css");
		}
	}
	
	/**
	* This method will be invoked after createScenes(). In this stage, all UI
	* components will be created with a non-NULL references for the UI components
	* that requires interaction (e.g. button click, or others).
	*/
	private void initEventHandlers() {
		initMainScreenHandlers();
		initImportScreenHandlers();
		initExportScreenHandlers();
		initOperationScreenHandlers();
		initFilterNumScreenHandlers();
		initFilterTextScreenHandlers();
		initLineChartSelectionNum1ScreenHandlers();
		initLineChartSelectionNum2ScreenHandlers();
		initPieChartSelectionTextScreenHandlers();
		initPieChartSelectionNumScreenHandlers();
		initLineChartScreenHandlers();
		initPieChartScreenHandlers();
		initAnimatedLineChartScreenHandlers();
	}
	
	

	//Initialize event handlers of every screen
	private void initMainScreenHandlers() {
		// click handler
		btImport.setOnAction(e -> {
			putSceneOnStage(SCENE_IMPORT_SCREEN);
		});
//		btExport.setOnAction(e -> {
//
//		});
		btDataset1.setOnAction(e -> {
			// In this example, we invoke SampleDataGenerator to generate sample data
			sampleDataTable = SampleDataGenerator.generateSampleLineData();
			lbOperationScreenSampleDataTable.setText(String.format("Dataset1: %d rows, %d columns", sampleDataTable.getNumRow(),
					sampleDataTable.getNumCol()));
			lbSampleDataTable.setText(String.format("Dataset1: %d rows, %d columns", sampleDataTable.getNumRow(),
					sampleDataTable.getNumCol()));
		});
		btDataset2.setOnAction(e -> {
			// In this example, we invoke SampleDataGenerator to generate sample data
			sampleDataTable = SampleDataGenerator.generateSampleLineDataV2();
			lbOperationScreenSampleDataTable.setText(String.format("Dataset2: %d rows, %d columns", sampleDataTable.getNumRow(),
					sampleDataTable.getNumCol()));
			lbSampleDataTable.setText(String.format("Dataset2: %d rows, %d columns", sampleDataTable.getNumRow(),
					sampleDataTable.getNumCol()));
		});
//		btDataset3.setOnAction(e -> {
//			
//		});
		btOperation.setOnAction(e -> {
			if(sampleDataTable != null) {
				putSceneOnStage(SCENE_OPERATION_SCREEN);
			}
			else{
				lbMainScreenComment.setText("ERROR: cannot operate with empty dataset");
			}
		});
//		btSaveDatasets.setOnAction(e -> {
//			
//		});
//		btLoadDatasets.setOnAction(e -> {
//			
//		});
//		btLoadChart.setOnAction(e -> {
//			
//		});
	}

	private void initImportScreenHandlers() {
		
	}

	private void initExportScreenHandlers() {
		
	}

	private void initOperationScreenHandlers() {
//		btFilterText.setOnAction(e -> {
//			
//		});
//		btFilterNum.setOnAction(e -> {
//			
//		});
		btPlotLineChart.setOnAction(e -> {
			putSceneOnStage(SCENE_LINE_CHART_SELECTION_NUM1_SCREEN);
		});
		btPlotPieChart.setOnAction(e -> {
			putSceneOnStage(SCENE_PIE_CHART_SELECTION_TEXT_SCREEN);
		});
		btOperationBackMain.setOnAction(e -> {
			putSceneOnStage(SCENE_MAIN_SCREEN);
		});
	}

	private void initFilterNumScreenHandlers() {
		
	}

	private void initFilterTextScreenHandlers() {
		
	}

	private void initLineChartSelectionNum1ScreenHandlers() {
		btLineChartSelectionNum1back.setOnAction(e -> {
			putSceneOnStage(SCENE_OPERATION_SCREEN);
		});
		btLineChartSelectionNum1continue.setOnAction(e -> {
			lineChartSelectionNum1input = tfLineChartSelectionNum1.getText();
			if(sampleDataTable.containsColumn(lineChartSelectionNum1input)==true &&
					sampleDataTable.getCol(lineChartSelectionNum1input).getTypeName().equals(DataType.TYPE_NUMBER)) {
				putSceneOnStage(SCENE_LINE_CHART_SELECTION_NUM2_SCREEN);
			}
			else {
				lbLineChartSelectionNum1.setText("Error: Invalid input");
			}
		});
	}

	private void initLineChartSelectionNum2ScreenHandlers() {
		btLineChartSelectionNum2back.setOnAction(e -> {
			putSceneOnStage(SCENE_OPERATION_SCREEN);
		});
		btLineChartSelectionNum2continue.setOnAction(e -> {
			lineChartSelectionNum2input = tfLineChartSelectionNum2.getText();
			lineChartSelectionTitle = tfLineChartSelectionTitle.getText();
			if (lineChartSelectionTitle.equals("")) {
				lineChartSelectionTitle = "Line Chart";
			}
			Boolean checkNotEqualAttitube = true;
			if((lineChartSelectionNum2input.equals(lineChartSelectionNum1input))) {
				checkNotEqualAttitube = false;
			}
			if(checkNotEqualAttitube && sampleDataTable.containsColumn(lineChartSelectionNum2input)==true && 
					sampleDataTable.getCol(lineChartSelectionNum2input).getTypeName().equals(DataType.TYPE_NUMBER)) {
				populateSampleDataTableValuesToLineChart("Line Chart");
				putSceneOnStage(SCENE_LINE_CHART_SCREEN);
			}
			else {
				if(!checkNotEqualAttitube) {
					lbLineChartSelectionNum2.setText("Error: selected attitube should not equal to the previous one");
				}
				else {
					lbLineChartSelectionNum2.setText("Error: Invalid input");
				}
			}
		});
	}

	private void initPieChartSelectionTextScreenHandlers() {
		btPieChartSelectionTextback.setOnAction(e -> {
			putSceneOnStage(SCENE_OPERATION_SCREEN);
		});
		btPieChartSelectionTextcontinue.setOnAction(e -> {
			pieChartSelectionTextInput = tfPieChartSelectionText.getText();
			if(sampleDataTable.containsColumn(pieChartSelectionTextInput)==true &&
					sampleDataTable.getCol(pieChartSelectionTextInput).getTypeName().equals(DataType.TYPE_STRING)) {
				putSceneOnStage(SCENE_PIE_CHART_SELECTION_NUM_SCREEN);
			}
			else {
				lbPieChartSelectionText.setText("Error: Invalid input");
			}
		});
	}

	private void initPieChartSelectionNumScreenHandlers() {
		btPieChartSelectionNumback.setOnAction(e -> {
			putSceneOnStage(SCENE_OPERATION_SCREEN);
		});
		btPieChartSelectionNumcontinue.setOnAction(e -> {
			pieChartSelectionNumInput = tfPieChartSelectionNum.getText();
			pieChartSelectionTitle = tfPieChartSelectionTitle.getText();
			if (pieChartSelectionTitle.equals("")) {
				pieChartSelectionTitle = "Pie Chart";
			}
			DataColumn dc = sampleDataTable.getCol(pieChartSelectionNumInput);
			boolean checkNegValue = true;
			for(Number num: (Number[])(dc.getData())){
				if(num.floatValue()<0) {
					checkNegValue = false;
				}
			}
			if(checkNegValue && sampleDataTable.containsColumn(pieChartSelectionNumInput)==true &&
					sampleDataTable.getCol(pieChartSelectionNumInput).getTypeName().equals(DataType.TYPE_NUMBER)) {
				populateSampleDataTableValuesToPieChart();
				putSceneOnStage(SCENE_PIE_CHART_SCREEN);
				
			}
			else {
				if(!checkNegValue) {
					lbPieChartSelectionNum.setText("Error: numbers in selected attibute should not be negative");
				}
				else {
					lbPieChartSelectionNum.setText("Error: Invalid input");
				}
			}
		});
	}

	private void initLineChartScreenHandlers() {
		btLineChartBackMain.setOnAction(e -> {
			putSceneOnStage(SCENE_MAIN_SCREEN);
		});
		btLineChartAnimated.setOnAction(e -> {
			populateSampleDataTableValuesToAnimatedLineChart();
			putSceneOnStage(SCENE_ANIMATED_LINE_CHART_SCREEN);
		});
//		btLineChartSave.setOnAction(e -> {
//		
//		});
	}
	
	private void initPieChartScreenHandlers() {
		btPieChartBackMain.setOnAction(e -> {
			putSceneOnStage(SCENE_MAIN_SCREEN);
		});
//		btPieChartSave.setOnAction(e -> {
//		
//		});
	}
	
	private void initAnimatedLineChartScreenHandlers() {
		btAnimatedLineChartBackMain.setOnAction(e -> {
			putSceneOnStage(SCENE_LINE_CHART_SCREEN);
		});
	}
	
	/**
	 * Populate sample data table values to the chart view
	 */
	private Pane paneMainScreen() {
		btImport = new Button("Import");
		btExport = new Button("Export");
		btDataset1 = new Button("Dataset1");
		btDataset2 = new Button("Dataset2");
		btDataset3 = new Button("Dataset3");
		btOperation = new Button("Filter Data or Plot Chart");
		btSaveDatasets = new Button("Save Datasets");
		btLoadDatasets = new Button("Load Datasets");
		btLoadChart = new Button("Load Chart");
				
		lbMainScreenTitle = new Label("COMP3111 Chart");
		lbSampleDataTable = new Label("DataTable: empty");
		lbMainScreenComment = new Label("Welcome for using hiGGWP program");

		// Layout the UI components
		HBox ie = new HBox(20);
		ie.setAlignment(Pos.CENTER);
		ie.getChildren().addAll(btImport, btExport);
		
		HBox ds = new HBox(20);
		ds.setAlignment(Pos.CENTER);
		ds.getChildren().addAll(btDataset1, btDataset2, btDataset3);
		
		HBox sl = new HBox(20);
		sl.setAlignment(Pos.CENTER);
		sl.getChildren().addAll(btSaveDatasets, btLoadDatasets, btLoadChart);
		
		VBox container = new VBox(20);
		container.getChildren().addAll(lbMainScreenTitle, ie, ds, lbSampleDataTable, new Separator(), btOperation, sl, lbMainScreenComment);
		container.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setCenter(container);

		// Apply style to the GUI components
		btOperation.getStyleClass().add("menu-button");
		lbMainScreenTitle.getStyleClass().add("menu-title");
		pane.getStyleClass().add("screen-background");
		
		return pane;
	}
	
	private Pane paneImportScreen() {
		btImportToDataset1=new Button("Import to dataset1");
		btImportToDataset2=new Button("Import to dataset2");
		btImportToDataset3=new Button("Import to dataset3");
		
		HBox I1=new HBox(20);
		I1.setAlignment(Pos.CENTER);
		I1.getChildren().addAll(btImportToDataset1, btImportToDataset2,btImportToDataset3);
		
		VBox container = new VBox(20);
		container.getChildren().addAll(I1);
		container.setAlignment(Pos.CENTER);
		
		BorderPane pane = new BorderPane();//delete it when edit
		pane.setCenter(container);
		
		lbOperationScreenTitle.getStyleClass().add("menu-title");
	pane.getStyleClass().add("screen-background");
		
		return pane;//delete it when edit
	}


	private Pane paneExportScreen() {
		BorderPane pane = new BorderPane();
		return pane;

	}


	private Pane paneOperationScreen() {
		btFilterText = new Button("Filter Text");
		btFilterNum = new Button("Filter Number");
		btPlotLineChart = new Button("Plot Line Chart");
		btPlotPieChart = new Button("Plot Pie Chart");
		btOperationBackMain = new Button("Back");
		
		lbOperationScreenSampleDataTable = new Label();
		lbOperationScreenTitle = new Label("Select Operation");
		lbFilterData = new Label("Filter Data");
		lbPlotChart = new Label("Plot Chart");
		
		HBox fd = new HBox(20);
		fd.setAlignment(Pos.CENTER);
		fd.getChildren().addAll(btFilterText, btFilterNum);
		
		HBox pc = new HBox(20);
		pc.setAlignment(Pos.CENTER);
		pc.getChildren().addAll(btPlotLineChart, btPlotPieChart);
		
		VBox container = new VBox(20);
		container.getChildren().addAll(lbOperationScreenTitle, lbOperationScreenSampleDataTable, lbFilterData, fd, new Separator(), lbPlotChart, pc, btOperationBackMain);
		container.setAlignment(Pos.CENTER);
		
		BorderPane pane = new BorderPane();
		pane.setCenter(container);

		lbOperationScreenTitle.getStyleClass().add("menu-title");
		pane.getStyleClass().add("screen-background");
		
		return pane;
	}


	private Pane paneFilterNumScreen() {
		BorderPane pane = new BorderPane();//delete it when edit
		return pane;//delete it when edit
	}


	private Pane paneFilterTextScreen() {
		BorderPane pane = new BorderPane();//delete it when edit
		return pane;//delete it when edit
	}


	private Pane paneLineChartSelectionNum1Screen() {
		btLineChartSelectionNum1continue = new Button("continue");
		btLineChartSelectionNum1back = new Button("back");
		lbLineChartSelectionNum1ScreenTitle = new Label("Input attibute name for x-axis");
		lbLineChartSelectionNum1Name = new Label("Name:"); 
		lbLineChartSelectionNum1 = new Label("Attibute: empty");
		
		tfLineChartSelectionNum1 = new TextField();
				
		HBox tf = new HBox(20);
		tf.setAlignment(Pos.CENTER);
		tf.getChildren().addAll(lbLineChartSelectionNum1Name, tfLineChartSelectionNum1);
		
		HBox bc = new HBox(20);
		bc.setAlignment(Pos.CENTER);
		bc.getChildren().addAll(btLineChartSelectionNum1back, btLineChartSelectionNum1continue);
		
		VBox container = new VBox(20);
		container.getChildren().addAll(lbLineChartSelectionNum1ScreenTitle, tf, lbLineChartSelectionNum1 ,bc);
		container.setAlignment(Pos.CENTER);
		
		BorderPane pane = new BorderPane();
		pane.setCenter(container);
		
		lbLineChartSelectionNum1ScreenTitle.getStyleClass().add("menu-title");
		pane.getStyleClass().add("screen-background");
		
		return pane;
	}

	private Pane paneLineChartSelectionNum2Screen() {
		btLineChartSelectionNum2continue = new Button("continue");
		btLineChartSelectionNum2back = new Button("back");
		lbLineChartSelectionNum2ScreenTitle = new Label("Input attibute name for y-axis");
		lbLineChartSelectionNum2Name = new Label("Name:"); 
		lbLineChartSelectionNum2 = new Label("Attibute: empty");
		lbLineChartSelectionTitle = new Label("(Optional)Name Chart Title");
		lbLineChartSelectionTitleName = new Label("Name:");
		
		tfLineChartSelectionNum2 = new TextField();
		tfLineChartSelectionTitle = new TextField();
				
		HBox tf = new HBox(20);
		tf.setAlignment(Pos.CENTER);
		tf.getChildren().addAll(lbLineChartSelectionNum2Name, tfLineChartSelectionNum2);
		
		HBox tf2 = new HBox(20);
		tf2.setAlignment(Pos.CENTER);
		tf2.getChildren().addAll(lbLineChartSelectionTitleName, tfLineChartSelectionTitle);
		
		HBox bc = new HBox(20);
		bc.setAlignment(Pos.CENTER);
		bc.getChildren().addAll(btLineChartSelectionNum2back, btLineChartSelectionNum2continue);
		
		VBox container = new VBox(20);
		container.getChildren().addAll(lbLineChartSelectionNum2ScreenTitle, tf, lbLineChartSelectionNum2,
				new Separator(), lbLineChartSelectionTitle, tf2, bc);
		container.setAlignment(Pos.CENTER);
		
		BorderPane pane = new BorderPane();
		pane.setCenter(container);
		
		lbLineChartSelectionNum2ScreenTitle.getStyleClass().add("menu-title");
		lbLineChartSelectionTitle.getStyleClass().add("menu-title");
		pane.getStyleClass().add("screen-background");
		
		return pane;
	}


	private Pane panePieChartSelectionTextScreen() {
		btPieChartSelectionTextcontinue = new Button("continue");
		btPieChartSelectionTextback = new Button("back");
		lbPieChartSelectionTextScreenTitle = new Label("Input attibute name for Label");
		lbPieChartSelectionTextName = new Label("Name:"); 
		lbPieChartSelectionText = new Label("Attibute: empty");
		
		tfPieChartSelectionText = new TextField();
		
				
		HBox tf = new HBox(20);
		tf.setAlignment(Pos.CENTER);
		tf.getChildren().addAll(lbPieChartSelectionTextName, tfPieChartSelectionText);
		
		HBox bc = new HBox(20);
		bc.setAlignment(Pos.CENTER);
		bc.getChildren().addAll(btPieChartSelectionTextback, btPieChartSelectionTextcontinue);
		
		VBox container = new VBox(20);
		container.getChildren().addAll(lbPieChartSelectionTextScreenTitle, tf, lbPieChartSelectionText ,bc);
		container.setAlignment(Pos.CENTER);
		
		BorderPane pane = new BorderPane();
		pane.setCenter(container);
		
		lbPieChartSelectionTextScreenTitle.getStyleClass().add("menu-title");
		pane.getStyleClass().add("screen-background");
		
		return pane;
	}


	private Pane panePieChartSelectionNumScreen() {
		btPieChartSelectionNumcontinue = new Button("continue");
		btPieChartSelectionNumback = new Button("back");
		lbPieChartSelectionNumScreenTitle = new Label("Input attibute name for value");
		lbPieChartSelectionNumName = new Label("Name:"); 
		lbPieChartSelectionNum = new Label("Attibute: empty");
		lbPieChartSelectionTitle = new Label("(Optional)Name Chart Title");
		lbPieChartSelectionTitleName = new Label("Name:");
		
		tfPieChartSelectionNum = new TextField();
		tfPieChartSelectionTitle = new TextField();
				
		HBox tf = new HBox(20);
		tf.setAlignment(Pos.CENTER);
		tf.getChildren().addAll(lbPieChartSelectionNumName, tfPieChartSelectionNum);
		
		HBox tf2 = new HBox(20);
		tf2.setAlignment(Pos.CENTER);
		tf2.getChildren().addAll(lbPieChartSelectionTitleName, tfPieChartSelectionTitle);
		
		HBox bc = new HBox(20);
		bc.setAlignment(Pos.CENTER);
		bc.getChildren().addAll(btPieChartSelectionNumback, btPieChartSelectionNumcontinue);
		
		VBox container = new VBox(20);
		container.getChildren().addAll(lbPieChartSelectionNumScreenTitle, tf, lbPieChartSelectionNum,
				new Separator(), lbPieChartSelectionTitle, tf2, bc);
		container.setAlignment(Pos.CENTER);
		
		BorderPane pane = new BorderPane();
		pane.setCenter(container);
		
		lbPieChartSelectionNumScreenTitle.getStyleClass().add("menu-title");
		lbPieChartSelectionTitle.getStyleClass().add("menu-title");
		pane.getStyleClass().add("screen-background");
		
		return pane;
	}


	private Pane paneLineChartScreen() {
		lineChartxAxis = new NumberAxis();
		lineChartyAxis = new NumberAxis();
		lineChart = new LineChart<Number, Number>(lineChartxAxis, lineChartyAxis);
				
		btLineChartBackMain = new Button("Back");
		btLineChartAnimated = new Button("Animated Chart");
		btLineChartSave = new Button("Save Chart");
		
		lineChartxAxis.setLabel("undefined");
		lineChartyAxis.setLabel("undefined");
		lineChart.setTitle("An empty line chart");

		HBox bc = new HBox(20);
		bc.setAlignment(Pos.CENTER);
		bc.getChildren().addAll(btLineChartBackMain, btLineChartSave);
		// Layout the UI components
		VBox container = new VBox(20);
		container.getChildren().addAll(lineChart, bc, btLineChartAnimated);
		container.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setCenter(container);

		// Apply CSS to style the GUI components
		pane.getStyleClass().add("screen-background");

		return pane;
	}


	private Pane panePieChartScreen() {
		pieChartData = FXCollections.observableArrayList(new PieChart.Data("", 1.0));
		pieChart = new PieChart(pieChartData);
		
		btPieChartBackMain = new Button("Back");
		btPieChartSave = new Button("Save Chart");
		
		pieChart.setTitle("An empty pie chart");
		
		HBox bs = new HBox(20);
		bs.setAlignment(Pos.CENTER);
		bs.getChildren().addAll(btPieChartBackMain, btPieChartSave);
		
		VBox container = new VBox(20);
		container.getChildren().addAll(pieChart ,bs);
		container.setAlignment(Pos.CENTER);
		
		BorderPane pane = new BorderPane();
		pane.setCenter(container);

		// Apply CSS to style the GUI components
		pane.getStyleClass().add("screen-background");

		return pane;
	}
	
	private Pane paneAnimatedLineChartScreen() {
		animatedLineChartxAxis = new NumberAxis();
		animatedLineChartyAxis = new NumberAxis();
		animatedLineChart = new LineChart<Number, Number>(animatedLineChartxAxis, animatedLineChartyAxis);
				
		btAnimatedLineChartBackMain = new Button("Back");
		
		animatedLineChartxAxis.setLabel("undefined");
		animatedLineChartyAxis.setLabel("undefined");
		animatedLineChart.setTitle("An empty line chart");
		
		VBox container = new VBox(20);
		container.getChildren().addAll(animatedLineChart, btAnimatedLineChartBackMain);
		container.setAlignment(Pos.CENTER);				
			
		BorderPane pane = new BorderPane();
		pane.setCenter(container);

		// Apply CSS to style the GUI components
		pane.getStyleClass().add("screen-background");

		return pane;
	}
	
	/**
	 * Populate sample data table values to the chart view
	 */
	private void populateSampleDataTableValuesToLineChart(String seriesName) {
		// Get 2 columns
		DataColumn xCol = sampleDataTable.getCol(lineChartSelectionNum1input);
		DataColumn yCol = sampleDataTable.getCol(lineChartSelectionNum2input);
		// Ensure both columns exist and the type is number
		if (xCol != null && yCol != null && xCol.getTypeName().equals(DataType.TYPE_NUMBER)
				&& yCol.getTypeName().equals(DataType.TYPE_NUMBER)) {
			lineChart.setTitle(lineChartSelectionTitle);
			lineChartxAxis.setLabel(lineChartSelectionNum1input);
			lineChartyAxis.setLabel(lineChartSelectionNum2input);
			// defining a series
			XYChart.Series<Number, Number> series = new XYChart.Series<>();
			series.setName(seriesName);
			// populating the series with data
			// As we have checked the type, it is safe to downcast to Number[]
			Number[] xValues = (Number[]) xCol.getData();
			Number[] yValues = (Number[]) yCol.getData();
			// In DataTable structure, both length must be the same
			int len = xValues.length;
			for (int i = 0; i < len; i++) {
				series.getData().add(new XYChart.Data<Number, Number>(xValues[i], yValues[i]));
			}
			// clear all previous series
			lineChart.getData().clear();
			// add the new series as the only one series for this line chart
			lineChart.getData().add(series);
		}
	}
	private void populateSampleDataTableValuesToPieChart() {
		DataColumn label = sampleDataTable.getCol(pieChartSelectionTextInput);
		DataColumn num = sampleDataTable.getCol(pieChartSelectionNumInput);
		
		if (label != null && num != null && label.getTypeName().equals(DataType.TYPE_STRING)
				&& num.getTypeName().equals(DataType.TYPE_NUMBER) && label.getSize()== num.getSize()) {
			ObservableList<PieChart.Data> newPieChartData = FXCollections.observableArrayList();
			for(int i=0;i<label.getSize();i++) {
				pieChart.setTitle(pieChartSelectionTitle);
				String[] labelData =(String[]) label.getData();
				Number[] numData = (Number[]) num.getData();
				newPieChartData.add(new PieChart.Data(labelData[i], (float) numData[i].floatValue()));
			}			
			pieChart.getData().clear();
			pieChart.getData().addAll(newPieChartData);
		}
	}
	private void populateSampleDataTableValuesToAnimatedLineChart() {
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		animatedLineChartxAxis.setForceZeroInRange(false);
		animatedLineChart.setLegendVisible(false);
		animatedLineChart.getData().clear();
		
		DataColumn xCol = sampleDataTable.getCol(lineChartSelectionNum1input);
		DataColumn yCol = sampleDataTable.getCol(lineChartSelectionNum2input);
		animatedLineChart.setTitle("Animated Line Chart");
		animatedLineChartxAxis.setLabel(lineChartSelectionNum1input);
		animatedLineChartyAxis.setLabel(lineChartSelectionNum2input);
	
		Number[] xValues = (Number[]) xCol.getData();
		Number[] yValues = (Number[]) yCol.getData();
		int valuesLength = xValues.length;
		valuesCount = 0;
				
		Task<Void> task = new Task<Void>() {
	        @Override protected Void call() throws Exception {
	        	boolean loop = true;
	            while (loop) {	            	
	                if (isCancelled()) break;
	                Platform.runLater(() -> {
	                    if (valuesCount<valuesLength) {
	                    	series.getData().add(new XYChart.Data<Number, Number>(xValues[valuesCount], yValues[valuesCount]));
	                    	valuesCount ++;
	                    }
	                    else {
	                    	series.getData().remove(0, xValues.length);;
	                    	series.getData().add(new XYChart.Data<Number, Number>(xValues[0], yValues[0]));
	                    	valuesCount = 1;
	                    }
	                	if (series.getData().size()>100) {
	                        series.getData().remove(0);
	                    }
	                	
	                });
	                Thread.sleep(1000);
	            }
	            return null;
	        }
	    };
	    animatedLineChart.getData().add(series);
	    Thread th = new Thread(task);
	    th.setDaemon(true);
	    th.start();
	}


	/**
	 * This method is used to pick anyone of the scene on the stage. It handles the
	 * hide and show order. In this application, only one active scene should be
	 * displayed on stage.
	 * 
	 * @param sceneID
	 *            - The sceneID defined above (see SCENE_XXX)
	 */
	private void putSceneOnStage(int sceneID) {
		// ensure the sceneID is valid
		if (sceneID < 0 || sceneID >= SCENE_NUM)
			return;
		stage.hide();
		stage.setTitle(SCENE_TITLES[sceneID]);
		stage.setScene(scenes[sceneID]);
		stage.setResizable(true);
		stage.show();
	}
	/**
	 * All JavaFx GUI application needs to override the start method You can treat
	 * it as the main method (i.e. the entry point) of the GUI application
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage; // keep a stage reference as an attribute
			initScenes(); // initialize the scenes
			initEventHandlers(); // link up the event handlers
			putSceneOnStage(SCENE_MAIN_SCREEN); // show the main screen
		} catch (Exception e) {
			e.printStackTrace(); // exception handling: print the error message on the console
		}
	}

	/**
	 * main method - only use if running via command line
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}