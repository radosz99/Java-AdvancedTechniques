package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.ChoiceFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.StringTokenizer;
import java.util.function.Consumer;

import generators.*;
import algs.*;
import base.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

public class MainController implements Initializable {
	  
	public LocalizedBinding localizedBinding;
    public ResourceBundle sources;
    Timeline clock = new Timeline();
	public final String qsort = "QuickSort";
	public final String csort = "CountingSort";
	public final String psort = "PigeonHoleSort";
	public final String isort = "InsertSort";
	@FXML private Label dataTypeLbl;
	@FXML private Label separatorInfoLbl;
	@FXML private Label separatorLbl;
	@FXML private Label rangeLbl;
	@FXML private Label minrangeLbl;
	@FXML private Label timeLbl;
	@FXML private ImageView imageview;
	@FXML private ImageView imageviewFlag;
	@FXML private Label quantLbl;
	@FXML private Label numbersQuantityLbl;
	@FXML private Label numberQuantityInfoLbl;
	@FXML private Label getInTypeLbl;
	@FXML private Label algorithmTypeLbl;
	@FXML private Label languageLbl;
	@FXML private Label descLbl;
	@FXML private Label stabil;
	@FXML private Label situ;
	@FXML private Label executionTimeLbl;
	@FXML private Label timeExLbl;
	@FXML private Label mainLbl;
	@FXML private MenuBar menu;
	@FXML private Menu file;
	@FXML private Menu help;
	@FXML private MenuItem fileSave;
	@FXML private MenuItem fileExit;
	@FXML private MenuItem helpInstruc;
	@FXML private MenuItem helpDesc;	
	@FXML private ComboBox <String> dataTypeCombo;
	@FXML private ComboBox <String> algorithmTypeCombo;
	@FXML private ComboBox <String>getInTypeCombo;
	@FXML private ComboBox <String> test;
	@FXML private ComboBox <Locale> lang;
	@FXML private Button getDataBtn;
	@FXML private Button sortBtn;
	@FXML private Button clrBtn;
	@FXML private Button clrElBtn;
	@FXML private Button shuffleBtn;
	@FXML private Button reverseBtn;
	@FXML private TextArea instructionDesc = new TextArea();
	@FXML private TextArea programDesc = new TextArea();
	@FXML private Button fileBtn;
	@FXML private TextField descText;
	@FXML private TextField minrangeText;
	@FXML private TextField rangeText;
	@FXML private TextField quantText;
	@FXML private TableView table;
	@FXML private TableColumn<IElement,Float> value;
	@FXML private TableColumn<IElement,String> word;
	List<IElement> dataToSort = new ArrayList<>();
	List<Element>dataToSort2 = new ArrayList<>();

	public ObservableList<IElement> list = FXCollections.observableArrayList();
	public ObservableList<Element> list2 = FXCollections.observableArrayList();
	public ObservableList<String> algsList = FXCollections.observableArrayList();
	public ObservableList<String> getInList = FXCollections.observableArrayList();
	
	public void castData() {
		dataToSort2.clear();
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
		DecimalFormat df = (DecimalFormat)nf;
		df.applyPattern("###.####");

		Spliterator<IElement> sItr = dataToSort.spliterator();
        sItr.forEachRemaining( (d -> {
        		dataToSort2.add(new Element(df.format(d.getValue()),d.getWord())); 
        	}));
        
		list2 = FXCollections.observableArrayList(dataToSort2);
		table.setItems(list2);
		
	}
	public void setData(ActionEvent event) throws IOException, InterruptedException {
		int minrange, range, quantity;

		try{
		    if(getInTypeCombo.getSelectionModel().getSelectedIndex()==1) {
		    	if(dataTypeCombo.getSelectionModel().getSelectedItem().toString().equals("integer")) {
		    		minrange = Integer.parseInt(minrangeText.getText());
				    range = Integer.parseInt(rangeText.getText());
				    quantity = Integer.parseInt(quantText.getText());
				    if(range<minrange){
				    	 warning("warnWrongFormat");
				    	 return;
				    }
				    
					IntElementGenerator data = new IntElementGenerator();
					dataToSort = data.getIntData(quantity,minrange,range);	
		    	}
		    	else if(dataTypeCombo.getSelectionModel().getSelectedItem().toString().equals("float")){
		    		minrange = Integer.parseInt(minrangeText.getText());
				   	range = Integer.parseInt(rangeText.getText());
				   	quantity = Integer.parseInt(quantText.getText());
				    if(range<minrange){
				    	 warning("warnWrongFormat");
				    	 return;
				    }

				    FloatElementGenerator data2 = new FloatElementGenerator();
					dataToSort = data2.getFloatData(quantity,minrange,range);	
		    	}
		    }
			else if(getInTypeCombo.getSelectionModel().getSelectedIndex()==2) {
				String value = minrangeText.getText();
				if(new DecimalFormat().getDecimalFormatSymbols().getDecimalSeparator() == ',') {
					value = value.replace('.', ';');
					value = value.replace(',', '.');
				}
				if(dataTypeCombo.getSelectionModel().getSelectedItem().toString().equals("integer"))
					dataToSort.add(0,new IntElement(quantText.getText(),Integer.parseInt(value)));
				else if(dataTypeCombo.getSelectionModel().getSelectedItem().toString().equals("float"))
					dataToSort.add(0,new FloatElement(quantText.getText(), Float.parseFloat(value)));
			}
		 } catch(NumberFormatException e){
		    warning("warnWrongFormat");
		 }	
		
		updateView();
	}		
	
	public void clearData(ActionEvent event) throws IOException, InterruptedException {
			list.clear();
			dataToSort.clear();
			updateView();
			
	}
	
	public void removeElement(ActionEvent event) throws IOException, InterruptedException {
		int index = table.getSelectionModel().getSelectedIndex();
		if(index!=-1) {
			dataToSort.remove((table.getSelectionModel().getFocusedIndex()));
			updateView();
		}
		else return;
}
	public void choiceFormatter() {
		int quantity = dataToSort2.size();
		String help = String.valueOf(quantity);
		
        char[] ch = new char[help.length()];
        for (int i = 0; i < help.length(); i++) { 
            ch[i] = help.charAt(i); 
        } 
        
		while(help.length()>2) {
	        char[] helpCh = new char[help.length()-1];
			for(int i=0; i<help.length()-1;i++) {
				ch[i]=ch[i+1];
				helpCh[i]=ch[i+1];
			}
			help = new String(helpCh);
		}
		
		String s = localizedBinding.getString("label");
		ChoiceFormat fmt = new ChoiceFormat(s);
		numbersQuantityLbl.setText(String.valueOf(quantity) + " "+ fmt.format(Integer.parseInt(help)));
	}
	
	public void sortData(ActionEvent event) {
		QuickSort qs = new QuickSort();
		InsertSort is = new InsertSort();
		PigeonHoleSort ps = new PigeonHoleSort();
		CountingSort cs = new CountingSort();
		if(list.size()==0) {
			warning("warnData");
		}
		
		else if(algorithmTypeCombo.getValue()==null){
			warning("warnAlg");
		}
		else {
			long start = System.nanoTime(); 
			if(algorithmTypeCombo.getSelectionModel().getSelectedItem().toString()==qsort) { 
				qs.solve(dataToSort);
				
			}
			else if(algorithmTypeCombo.getSelectionModel().getSelectedItem().toString()==isort) {
				is.solve(dataToSort);
			}
			else if(algorithmTypeCombo.getSelectionModel().getSelectedItem().toString()==psort) {
				ps.solve(dataToSort);
			}
			else if(algorithmTypeCombo.getSelectionModel().getSelectedItem().toString()==csort) {
				cs.solve(dataToSort);
			}
			long elapsedTime = System.nanoTime() - start;
			if (elapsedTime>1000000000) {
				executionTimeLbl.setText(elapsedTime/1000000000 + " s");
			}
			else if(elapsedTime>1000000)
				executionTimeLbl.setText(elapsedTime/1000000 + " ms");
			else if(elapsedTime>1000)
				executionTimeLbl.setText(elapsedTime/1000 + " μs");
			else
				executionTimeLbl.setText(elapsedTime + " ns");
		}
		
		updateView();
	}
	
	public void information(String key) {
	   	Alert alert = new Alert(AlertType.INFORMATION);
	   	alert.titleProperty().bind(localizedBinding.createStringBinding("informationDialog"));
	   	alert.setHeaderText(null);
	   	alert.contentTextProperty().bind(localizedBinding.createStringBinding(key));
	   	alert.showAndWait();
	}
	
	public void warning(String key) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.titleProperty().bind(localizedBinding.createStringBinding("warn"));
		alert.headerTextProperty().bind(localizedBinding.createStringBinding(key));
		alert.contentTextProperty().bind(localizedBinding.createStringBinding("care"));
		alert.showAndWait();
	}
	public void setLabels(boolean stab, boolean sit) {
		if(stab)
			stabil.textProperty().bind(localizedBinding.createStringBinding("stabil"));
		else
			stabil.textProperty().bind(localizedBinding.createStringBinding("notstabil"));
        
		if(sit)
			situ.textProperty().bind(localizedBinding.createStringBinding("situ"));
		else
			situ.textProperty().bind(localizedBinding.createStringBinding("notsitu"));
	}

	public void handleAlgComboBox () {
        if(algorithmTypeCombo.getSelectionModel().getSelectedItem()!=null) {
	        if(algorithmTypeCombo.getSelectionModel().getSelectedItem().equals(qsort)) {
	            Image i = new Image(new File("gifs/qs.gif").toURI().toString());
	            //https://commons.wikimedia.org/wiki/File:Quicksort-example.gif
	            imageview.setImage(i);
	         	QuickSort qs = new QuickSort();
	           	setLabels(qs.isStable(), qs.isInSitu());
	           	descText.setText(qs.description());
	        }
	        else if(algorithmTypeCombo.getSelectionModel().getSelectedItem().equals(isort)) {
	            Image i = new Image(new File("gifs/is.gif").toURI().toString());
	            //https://commons.wikimedia.org/wiki/File:Insertion-sort-example.gif
	            imageview.setImage(i);
	            InsertSort qs = new InsertSort();
	            setLabels(qs.isStable(), qs.isInSitu());
	            descText.setText(qs.description());
	        }
	        else if(algorithmTypeCombo.getSelectionModel().getSelectedItem().equals(csort)) {
	            Image i = new Image(new File("gifs/cs.gif").toURI().toString());
	            //https://github.com/Lugriz/typescript-algorithms/tree/master/src/algorithms/sorting/counting-sort
	            imageview.setImage(i);
	          	CountingSort qs = new CountingSort();
	            setLabels(qs.isStable(), qs.isInSitu());
	            descText.setText(qs.description());
	        }
	        else if(algorithmTypeCombo.getSelectionModel().getSelectedItem().equals(psort)) {
	          	PigeonHoleSort qs = new PigeonHoleSort();
	           	imageview.setImage(null);
	            setLabels(qs.isStable(), qs.isInSitu());
	            descText.setText(qs.description());
	        }
        }
            
	}
	
	public void handleDataTypeComboBox(){
       	algorithmTypeCombo.setDisable(false);
       	getInTypeCombo.setDisable(false);
    	algorithmTypeCombo.promptTextProperty().unbind();
    	algorithmTypeCombo.promptTextProperty().set("");
       	getInTypeCombo.promptTextProperty().unbind();
       	getInTypeCombo.promptTextProperty().set("");
       	
        if(dataTypeCombo.getItems().get(dataTypeCombo.getSelectionModel().getSelectedIndex()).equals("integer")) {
    		separatorInfoLbl.setVisible(false);
    		separatorLbl.setVisible(false);
       		list.clear();
       		dataToSort.clear();
       		table.setItems(list);
       		if(algsList.size()==2)
       			algsList.addAll(csort, psort);
       		else if(algsList.size()==0)
       			algsList.addAll(qsort, isort, csort, psort);
         }
        else if(dataTypeCombo.getItems().get(dataTypeCombo.getSelectionModel().getSelectedIndex()).equals("float")) {
    		separatorInfoLbl.setVisible(true);
    		separatorLbl.setVisible(true);
       		if(algsList.size()==4 && algorithmTypeCombo.getSelectionModel().getSelectedIndex()<2) {
       			algsList.remove(2);
       			algsList.remove(2);
       		}
       		else if(algsList.size()==0)
       			algsList.addAll(qsort, isort);
       		else {
       			algsList.clear();
       			algsList.addAll(qsort, isort);
       	       	descText.setText("");
       	    	stabil.textProperty().unbind();
       	    	stabil.textProperty().set("");
       	    	situ.textProperty().unbind();
       	    	situ.textProperty().set("");
       	    	imageview.setImage(null);
       		}
       			
        }
           
	}
	
	public void saveToFile(ActionEvent event) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
		for(Element i : dataToSort2) {
			writer.write(i.getValue()+";"+i.getWord());
			writer.newLine();
	   	}
	  	information("informationDialogExport");
	    writer.close();
	}
	
	public void closeApp(ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}
	
	public <T> void showInfo(T item, int x, int y) {
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add((Node) item);
        Scene secondScene = new Scene(secondaryLayout, x, y);
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);
        newWindow.show();
	}
	
	public void showFormatInfo(ActionEvent event) throws IOException {
		instructionDesc.textProperty().bind(localizedBinding.createStringBinding("instrDesc"));
		showInfo(instructionDesc,300,200);
	}
	
	public void showProgramInfo(ActionEvent event) throws IOException {
		programDesc.textProperty().bind(localizedBinding.createStringBinding("programDesc"));
		showInfo(programDesc,300,200);
	}
	
	public void handleFile() throws FileFormatException, FileNotFoundException {
		list.clear();
		dataToSort.clear();
		table.setItems(list);
		
		FileChooser fc = new FileChooser();
		File selectedFile = fc.showOpenDialog(null);
		if(selectedFile==null) return;
		Scanner scan = new Scanner(selectedFile);
		
		while(scan.hasNext()) {
			String line = scan.nextLine();
			String[] parts = line.split(";");
			if(parts.length != 2) {
				scan.close();
				warning("warnFile");
				return;
			}
			String word = parts[1];
			
			try {
				if(dataTypeCombo.getSelectionModel().getSelectedItem().toString().equals("float")) {
					parts[0] = parts[0].replace(',', '.');
					float size = Float.parseFloat(parts[0]);
					dataToSort.add(new FloatElement(word,size));
				} else if(dataTypeCombo.getSelectionModel().getSelectedItem().toString().equals("integer")){
					int size = Integer.parseInt(parts[0]);
					dataToSort.add(new IntElement(word,size));
				}
			} catch(NumberFormatException e){
				warning("warnFile");
				return;
		    }

			updateView();
			
		}
		scan.close();
	}
	
	public void updateView() {
		castData();
		list = FXCollections.observableArrayList(dataToSort);
		list2 = FXCollections.observableArrayList(dataToSort2);
		numbersQuantityLbl.setText(String.valueOf(dataToSort2.size()));
		choiceFormatter();
		table.setItems(list2);
	}
	
	public void shuffleList(){
		Collections.shuffle(dataToSort);
		updateView();
	}

	
	public void reverseList(){
		Collections.reverse(dataToSort);
		updateView();
	}
	
	public void updateDateAndFlag() {
    	Locale.setDefault(lang.getSelectionModel().getSelectedItem());
        DateTimeFormatter localeFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        clock.stop();
        clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            timeLbl.setText(LocalDateTime.now().format(localeFormat));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        Image i = new Image(new File("icons/"+Locale.getDefault().getCountry().toString()+ ".png").toURI().toString());
        imageviewFlag.setImage(i);
        getInList.set(0, localizedBinding.getString("loadFile"));
        getInList.set(1, localizedBinding.getString("random"));
        getInList.set(2, localizedBinding.getString("hand"));
		separatorLbl.setText(Character.toString(new DecimalFormat().getDecimalFormatSymbols().getDecimalSeparator()));
		updateView();
		
	}
	
	public void hideFileButton() {
    	rangeLbl.setVisible(true);
    	minrangeLbl.setVisible(true);
    	quantLbl.setVisible(true);
		rangeText.setVisible(true);
		minrangeText.setVisible(true);
		quantText.setVisible(true);
    	getDataBtn.setVisible(true);
    	fileBtn.setVisible(false);
	}
	
	public void handleGetInTypeComboBox(){
		rangeText.setText("");
		minrangeText.setText("");
		quantText.setText("");
        if(getInTypeCombo.getSelectionModel().getSelectedIndex()==0) {
          	rangeLbl.setVisible(false);
          	minrangeLbl.setVisible(false);
           	quantLbl.setVisible(false);
       		rangeText.setVisible(false);
       		minrangeText.setVisible(false);
       		quantText.setVisible(false);
       		getDataBtn.setVisible(false);
       		fileBtn.setVisible(true);
            fileBtn.setOnAction(f->{try {
            	if(dataTypeCombo.getValue()==null){
            		warning("warnType");
           		} else handleFile();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FileFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					}});
        }     
        else if(getInTypeCombo.getSelectionModel().getSelectedIndex()==1) {
           	hideFileButton();
           	minrangeLbl.textProperty().bind(localizedBinding.createStringBinding("minrangeLbl"));
           	rangeLbl.textProperty().bind(localizedBinding.createStringBinding("rangeLbl"));
           	quantLbl.textProperty().bind(localizedBinding.createStringBinding("quantLbl"));
            getDataBtn.textProperty().bind(localizedBinding.createStringBinding("generate"));
        }        
        else if(getInTypeCombo.getSelectionModel().getSelectedIndex()==2) {
           	hideFileButton();
           	rangeLbl.setVisible(false);
           	rangeText.setVisible(false);
           	minrangeLbl.textProperty().bind(localizedBinding.createStringBinding("number"));
          	quantLbl.textProperty().bind(localizedBinding.createStringBinding("word"));
          	getDataBtn.textProperty().bind(localizedBinding.createStringBinding("newelement"));
        }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	Main.getPrimaryStage().getIcons().add(new Image("file:icon.png"));
    	
        lang.getItems().addAll(new Locale("pl","PL"), 
        		new Locale("en","US"), 
        		new Locale("en","GB"), 
        		new Locale("de", "DE"),
        		new Locale("fr", "FR"),
        		new Locale("ja","JP"));

//		add all countries
//		String count[] = Locale.getISOCountries();
//		Locale locale;
//		for(String s : count){	
//			locale = new Locale(s);
//			s = s + "-" + locale.getLanguage();
//			locale = new Locale.Builder().setLanguageTag(s).build();
//			lang.getItems().addAll(locale);
//		}
      
        lang.setCellFactory(lv -> {
        	return createListCell();
        });
        lang.setButtonCell(createListCell());

        localizedBinding = new LocalizedBinding("resources/bundles", Locale.getDefault());
        localizedBinding.localeProperty().bind(lang.valueProperty());
        
        setAllStrings();
        
        lang.getSelectionModel().select(Locale.getDefault());
        
		sources = arg1;
		choiceFormatter();
        lang.setOnAction(lv -> {
        	updateDateAndFlag();
        });
        
        dataTypeCombo.setOnAction(lv -> {
            handleDataTypeComboBox();
        });
        
        algorithmTypeCombo.setOnAction(lv -> {
        	handleAlgComboBox();
        });
        
        getInTypeCombo.setOnAction(lv -> {
        	handleGetInTypeComboBox();
        });
        
        updateDateAndFlag();
		table.setItems(list);
		
		
		lang.setCellFactory(new Callback<ListView<Locale>, ListCell<Locale>>() {
		    @Override
		    public ListCell<Locale> call(ListView<Locale> p) {
		        return new ListCell<Locale>() {
		            @Override
		            protected void updateItem(Locale item, boolean empty) {
		                super.updateItem(item, empty);
		                if (empty) {
		                    setText("");
		                } else {
		                    setText(item.getDisplayCountry(item));
		                }
		                if (item == null || empty) {
		                    setGraphic(null);
		                } else {
		                    Image icon = null;
		                    try {
		                    	icon = new Image(new File("icons/"+item.getCountry().toString()+".png").toURI().toString());
		                    } catch(NullPointerException ex) {
		                    	System.out.println("brak ikony");
		                    }
		                    ImageView iconImageView = new ImageView(icon);
		                    iconImageView.setFitHeight(10);
		                    iconImageView.setPreserveRatio(true);
		                    setGraphic(iconImageView);
		                }
		            }
		        };
		    }
		});
	}
	
    private ListCell<Locale> createListCell() {
        return new ListCell<Locale>() {
            @Override
            public void updateItem(Locale locale, boolean empty) {
                super.updateItem(locale, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(locale.getDisplayCountry(locale));
                }
            }
        };
    }
    
    private void setAllStrings() {
		getInTypeCombo.setItems(getInList);
        value.textProperty().bind(localizedBinding.createStringBinding("value"));
        word.textProperty().bind(localizedBinding.createStringBinding("word2"));
        getInList.addAll(localizedBinding.getString("loadFile"),localizedBinding.getString("random"), localizedBinding.getString("hand"));
        
		algorithmTypeCombo.setItems(algsList);
		value.setCellValueFactory(new PropertyValueFactory<IElement, Float >("value"));
		word.setCellValueFactory(new PropertyValueFactory<IElement, String >("word"));
		minrangeText.setVisible(false);
		rangeText.setVisible(false);
		algorithmTypeCombo.setDisable(true);
		getInTypeCombo.setDisable(true);
		quantText.setVisible(false);
		rangeLbl.setVisible(false);
		quantLbl.setVisible(false);
		fileBtn.setVisible(false);
		getDataBtn.setVisible(false);
		separatorInfoLbl.setVisible(false);
		separatorLbl.setVisible(false);
		
    	sortBtn.textProperty().bind(localizedBinding.createStringBinding("sortBtn"));
    	algorithmTypeLbl.textProperty().bind(localizedBinding.createStringBinding("algorithmTypeLbl"));
    	clrBtn.textProperty().bind(localizedBinding.createStringBinding("clrBtn"));
    	clrElBtn.textProperty().bind(localizedBinding.createStringBinding("clrElBtn"));
    	algorithmTypeCombo.promptTextProperty().bind(localizedBinding.createStringBinding("dataFirst"));
    	getInTypeCombo.promptTextProperty().bind(localizedBinding.createStringBinding("dataFirst"));
    	dataTypeLbl.textProperty().bind(localizedBinding.createStringBinding("dataTypeLbl"));
    	descLbl.textProperty().bind(localizedBinding.createStringBinding("descLbl"));
    	helpDesc.textProperty().bind(localizedBinding.createStringBinding("descProgram"));
    	languageLbl.textProperty().bind(localizedBinding.createStringBinding("language"));
    	timeExLbl.textProperty().bind(localizedBinding.createStringBinding("executionTimeLbl"));
    	fileExit.textProperty().bind(localizedBinding.createStringBinding("exit"));
    	file.textProperty().bind(localizedBinding.createStringBinding("file"));
    	fileBtn.textProperty().bind(localizedBinding.createStringBinding("fileBtn"));
    	//getInTypeCombo.promptTextProperty().bind(localizedBinding.createStringBinding("getInTypeCombo"));
    	help.textProperty().bind(localizedBinding.createStringBinding("help"));
    	helpInstruc.textProperty().bind(localizedBinding.createStringBinding("instr"));
    	mainLbl.textProperty().bind(localizedBinding.createStringBinding("mainLbl"));
    	separatorInfoLbl.textProperty().bind(localizedBinding.createStringBinding("infoSeparatorLbl"));
    	reverseBtn.textProperty().bind(localizedBinding.createStringBinding("reverseBtn"));
    	shuffleBtn.textProperty().bind(localizedBinding.createStringBinding("shuffleBtn"));
    	getInTypeLbl.textProperty().bind(localizedBinding.createStringBinding("getInTypeCombo"));
    	fileSave.textProperty().bind(localizedBinding.createStringBinding("save"));
    	Main.getPrimaryStage().titleProperty().bind(localizedBinding.createStringBinding("mainLbl"));
    	numberQuantityInfoLbl.textProperty().bind(localizedBinding.createStringBinding("numberQuantityInfoLbl"));

    }

    
	
}
