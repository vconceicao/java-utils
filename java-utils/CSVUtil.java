package br.gov.ibge.concurso.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

public class CSVUtil {
	
	private static final String CSV_SEPARADOR = ";";
	
	public static StringBuffer gerarCSV(String... colunas){
		return novaLinha(new StringBuffer(), colunas);
	}
	
	public static StringBuffer novaLinha(StringBuffer sb, String... linha){
		
		for(String l : linha){
			if(!StringUtils.isEmpty(l)){
				sb.append(l);
			}
			sb.append(CSV_SEPARADOR);			
		}
		
		sb.append(System.lineSeparator());
		
		return sb;
		
	}
	
	public static byte[] exportarBytes(StringBuffer sb){
		return sb.toString().getBytes();
	}
	
	public static BufferedReader importarBytes(byte[] bytes){
		return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
	}
	

	public static List<String> lerProximaLinha(BufferedReader br) throws IOException{
		String linha = br.readLine();
		if(linha != null){
			return Arrays.asList(linha);
		}
		return Collections.emptyList();
	}
	
	public static List<String> lerSegundaLinha(byte[] bytes) throws IOException{
		
		BufferedReader br = importarBytes(bytes);
		
		
		br.readLine();// consume first line and ignore
		String linha = br.readLine();
		if(linha != null){
			return Arrays.asList(linha.split(CSV_SEPARADOR));
		}
		return Collections.emptyList();
	}
	
	public static Map<Integer, List<String>> lerTodasLinhas(byte[] bytes){
		
		BufferedReader br = importarBytes(bytes);
		
		Map<Integer, List<String>> linhas = new TreeMap<>();
		
		br.lines().forEach(l->linhas.put(linhas.size(), Arrays.asList(l.split(CSV_SEPARADOR))));
		
		return linhas;
	}

	public static List<Long> contaSeparadores(byte[] bytes){
		
		BufferedReader br = importarBytes(bytes);
		
		List<Long> contadores = new ArrayList<Long>();
		
		br.lines().forEach(l-> contadores.add(l.chars().filter(e -> e == ';').count()));
		
		return contadores;
	}
	
	
	
	
}
