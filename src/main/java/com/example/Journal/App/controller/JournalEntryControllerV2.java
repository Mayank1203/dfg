package com.example.Journal.App.controller;

import com.example.Journal.App.entry.JournalEntry;
import com.example.Journal.App.repository.JournalEntryRepository;
import com.example.Journal.App.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;


    @GetMapping
    public List<JournalEntry> getAll(){        //localhost:8080/journal -> GET
     return  journalEntryService.getAll();
    }

    @PostMapping
    public JournalEntry CreateEntry(@RequestBody JournalEntry myEntry){ // localhost:8080/journal -> POST
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry ;
    }

    @GetMapping("id/{MyId}")
    public JournalEntry GetjournalEntryById(@PathVariable ObjectId MyId){
        return journalEntryService.findById(MyId).orElse(null);
    }

    @DeleteMapping("id/{MyId}")
    public boolean DeletejournalEntryById(@PathVariable ObjectId MyId){
        journalEntryService.deleteById(MyId);
        return true;
    }

    @PutMapping("id/{id}")
    public JournalEntry journalEntryById(@PathVariable ObjectId id , @RequestBody JournalEntry newEntry){
        JournalEntry oldEntry = journalEntryService.findById(id).orElse(null);

        if(oldEntry != null){
            oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("")? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.equals("") ? newEntry.getContent(): oldEntry.getContent());
        }

        journalEntryService.saveEntry(oldEntry);
        return oldEntry ;
    }

}

