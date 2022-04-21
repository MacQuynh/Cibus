package dk.au.mad22spring.group04.cibusapp.helpers;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dk.au.mad22spring.group04.cibusapp.model.DTOs.InstructionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.SectionDTO;

public class Converters {

    @TypeConverter
    public String toInstruction(InstructionDTO instructionDTO){

        Gson gson = new GsonBuilder().create();
        return gson.toJson(instructionDTO);
    }
    @TypeConverter
    public InstructionDTO fromInstruction(String instructionDTO){

        Gson gson = new GsonBuilder().create();
        return gson.fromJson(instructionDTO, InstructionDTO.class);
    }

    @TypeConverter
    public SectionDTO fromSection(String sectionDTO){

        Gson gson = new GsonBuilder().create();
        return gson.fromJson(sectionDTO, SectionDTO.class);
    }

    @TypeConverter
    public String toSection(SectionDTO sectionDTO){

        Gson gson = new GsonBuilder().create();
        return gson.toJson(sectionDTO);
    }
}
