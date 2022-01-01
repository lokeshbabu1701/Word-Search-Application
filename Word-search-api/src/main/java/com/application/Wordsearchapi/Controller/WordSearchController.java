package com.application.Wordsearchapi.Controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.Wordsearchapi.Service.WordSearchGridService;

@RestController("/")
public class WordSearchController {
	
	@Autowired
	WordSearchGridService wordSearchGridService;

	@GetMapping("/wordSearch")
	public String createWordGrid(@RequestParam("gridSize") int gridSize, @RequestParam("searchContent") String contents) {
		String gridToString = "";
		List<String> contentList = Arrays.asList(contents.split("-"));
		char[][] gridValue = wordSearchGridService.generateGrid(gridSize, contentList);
		for(int i = 0; i < gridSize; i++) {
			for(int j = 0; j < gridSize; j++) {
				gridToString = gridToString + gridValue[i][j] + " ";
			}
			gridToString = gridToString + "\r\n";
		}
		return gridToString;
	}
	
}
