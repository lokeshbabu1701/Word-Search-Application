package com.application.Wordsearchapi.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

@Service
public class WordSearchGridService {

	/**
	 * Generating Grid with the specific contents
	 * 
	 * @param gridSize
	 * @param words
	 * @return
	 */
	public char[][] generateGrid(int gridSize, List<String> words) {
		List<Coordinate> coordinates = new ArrayList<>();
		char[][] contents = new char[gridSize][gridSize];
		for(int i = 0; i < gridSize; i++) {
			for(int j = 0; j < gridSize; j++) {
				coordinates.add(new Coordinate(i, j)); 
				contents[i][j] = '-';
			}
		}
		Collections.shuffle(coordinates);
		for(String word : words) {
			for(Coordinate coordinate : coordinates) {
				int x = coordinate.x;
				int y = coordinate.y;
				Direction selectedDirection = getDirectionForFit(contents, word, coordinate);
				if(selectedDirection != null) {
					switch(selectedDirection) {
					case HORIZONTAL:
						for(char ch : word.toCharArray()) {
							contents[x][y++] = ch;
						}
						break;
					case VERTICAL :
						for(char ch : word.toCharArray()) {
							contents[x++][y] = ch;
						}
						break;
					case DIAGONAL :
						for(char ch : word.toCharArray()) {
							contents[x++][y++] = ch;
						}
						break;
					case HORIZONTAL_INVERSE:
						for(char ch : word.toCharArray()) {
							contents[x][y--] = ch;
						}
						break;
					case VERTICAL_INVERSE :
						for(char ch : word.toCharArray()) {
							contents[x--][y] = ch;
						}
						break;
					case DIAGONAL_INVERSE :
						for(char ch : word.toCharArray()) {
							contents[x--][y--] = ch;
						}
						break;
					}
					break;
				}
			}
		}
		randomFillGrid(contents);
		return contents;
	}
	
	/**
	 * Finding Direction for the Fitting segment
	 * 
	 * @param contents
	 * @param word
	 * @param coordinate
	 * @return
	 */
	private Direction getDirectionForFit(char[][] contents, String word, Coordinate coordinate) {
		List<Direction> directions = Arrays.asList(Direction.values());
		Collections.shuffle(directions);
		for(Direction direction : directions) {
			 if(doesFit(contents, word, coordinate, direction)) {
				 return direction;
			 }
		}
		return null;
	}
	
	/**
	 * Checking the Boundary of the Grid
	 * 
	 * @param contents
	 * @param word
	 * @param coordinate
	 * @param direction
	 * @return
	 */
	private boolean doesFit(char[][] contents, String word, Coordinate coordinate, Direction direction) {
		int gridSize = contents[0].length;
		switch(direction) {
			case HORIZONTAL :
				if((coordinate.y + word.length()) > gridSize) return false;
				for(int i = 0; i< word.length(); i++) {
					if(contents[coordinate.x][coordinate.y + i] != '-') return false;
				}
				break;
			case VERTICAL:
				if((coordinate.x + word.length()) > gridSize) return false;
				for(int i = 0; i< word.length(); i++) {
					if(contents[coordinate.x+i][coordinate.y] != '-') return false;
				}
				break;
			case DIAGONAL:
				if((coordinate.x + word.length()) > gridSize || (coordinate.y + word.length()) > gridSize) return false;
				for(int i = 0; i< word.length(); i++) {
					if(contents[coordinate.x+i][coordinate.y + i] != '-') return false;
				}
				break;
			case HORIZONTAL_INVERSE :
				if(coordinate.y < word.length()) return false;
				for(int i = 0; i< word.length(); i++) {
					if(contents[coordinate.x][coordinate.y - i] != '-') return false;
				}
				break;
			case VERTICAL_INVERSE:
				if(coordinate.x < word.length()) return false;
				for(int i = 0; i< word.length(); i++) {
					if(contents[coordinate.x - i][coordinate.y] != '-') return false;
				}
				break;
			case DIAGONAL_INVERSE:
				if(coordinate.x < word.length() || coordinate.y < word.length()) return false;
				for(int i = 0; i < word.length(); i++) {
					if(contents[coordinate.x - i][coordinate.y - i] != '-') return false;
				}
				
				break;
		}
		return true;
	}
	
	/**
	 * Display the Grid
	 * 
	 * @param contents
	 */
	public void displayGrid(char[][] contents) {
		int gridSize = contents[0].length;
		for(int i = 0;i < gridSize; i++) {
			for(int j = 0; j < gridSize; j++) {
				System.out.print(contents[i][j]+ " ");
			}
			System.out.println(" ");
		}
	}
	
	/**
	 * Display the Randome Content to the Grid
	 * 
	 * @param contents
	 */
	public void randomFillGrid(char[][] contents) {
		int gridSize = contents[0].length;
		String randomContent = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for(int i = 0;i < gridSize; i++) {
			for(int j = 0;j < gridSize; j++) {
				if(contents[i][j] == '-') {
					int randomIndex = ThreadLocalRandom.current().nextInt(0, randomContent.length());
					contents[i][j] = randomContent.charAt(randomIndex);
				}
			}
		}
	}
	
	/**
	 * ENUM for Direction
	 * 
	 * @author lokesh
	 *
	 */
	private enum Direction {
		HORIZONTAL,
		HORIZONTAL_INVERSE,
		VERTICAL,
		VERTICAL_INVERSE,
		DIAGONAL,
		DIAGONAL_INVERSE
	}
	
	/**
	 * Coordinate for the Grid
	 * 
	 * @author lokesh
	 *
	 */
	private class Coordinate {
		private int x;
		private int y;
		
		Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
}
