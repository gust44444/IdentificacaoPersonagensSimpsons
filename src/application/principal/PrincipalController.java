package application.principal;

import java.io.File;
import java.text.DecimalFormat;

import application.extract_feature.AprendizagemBayesiana;
import application.extract_feature.ExtractFeature;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class PrincipalController {
	
	@FXML
	private ImageView imageView;
	@FXML private Label l3;
	@FXML private Label lbPctNedFlanders;
	@FXML private Label lbPctKrusty;
	@FXML private Label lbCamisaNed;
	@FXML private Label lbBarbaNed;
	@FXML private Label lbCabeloKrusty;
	@FXML private Label lbBocaKrusty;
	@FXML private Label lbNarizKrusty;
	@FXML private Label lbMatrizColisao;
	
	private DecimalFormat df = new DecimalFormat("##0.0000");
	private double[] caracteristicasImgSel = {0,0,0,0,0};
	
	@FXML
	public void classificar() {
		//********Naive Bayes
		double[] nb = AprendizagemBayesiana.naiveBayes(caracteristicasImgSel);
		lbPctNedFlanders.setText("NED FLANDERS: "+df.format(nb[0]*100) + "%");
		lbPctKrusty.setText("KRUSTY: "+df.format(nb[1]*100) + "%");
		
	}
	
	@FXML
	public void extrairCaracteristicas() {
		ExtractFeature.extrair();
	}
	
	@FXML
	public void selecionaImagem() {
		File f = buscaImg();
		if(f != null) {
			Image img = new Image(f.toURI().toString());
			imageView.setImage(img);
			imageView.setFitWidth(img.getWidth());
			imageView.setFitHeight(img.getHeight());
			caracteristicasImgSel = ExtractFeature.extraiCaracteristicas(f);
			for (double d : caracteristicasImgSel) {
				System.out.println(d);
			}
		}
		
		//Características Ned
		lbCamisaNed.setText("Camisa Ned Flanders:" + caracteristicasImgSel[0]);
		lbBarbaNed.setText("Barba Ned Flanders:" + caracteristicasImgSel[1]);
		
		//Características Krusty
		lbCabeloKrusty.setText("Cabelo Krusty:" + caracteristicasImgSel[3]);
		lbBocaKrusty.setText("Boca Krusty:" + caracteristicasImgSel[4]);
		lbNarizKrusty.setText("Nariz Krusty:" + caracteristicasImgSel[5]);
	}
	
	private File buscaImg() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new 
				   FileChooser.ExtensionFilter(
						   "Imagens", "*.jpg", "*.JPG", 
						   "*.png", "*.PNG", "*.gif", "*.GIF", 
						   "*.bmp", "*.BMP")); 	
		 fileChooser.setInitialDirectory(new File("src/imagens"));
		 File imgSelec = fileChooser.showOpenDialog(null);
		 try {
			 if (imgSelec != null) {
			    return imgSelec;
			 }
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		 return null;
	}
	
	@FXML
	public void mostraMatrizDecisao() throws Exception {
		lbMatrizColisao.setText(AprendizagemBayesiana.gerarMatrizConfusao());
	}
	
}
