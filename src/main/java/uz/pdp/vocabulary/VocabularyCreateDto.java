package uz.pdp.vocabulary;


import java.util.List;

public record VocabularyCreateDto(String word,List<String> translations,List<String> synonyms,List<String> examples){}
