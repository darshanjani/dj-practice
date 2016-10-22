package com.dj.controller;

import com.dj.model.CustomResponse;
import com.dj.model.User;
import com.dj.repository.UserRepository;
import com.dj.storage.StorageFileNotFoundException;
import com.dj.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Controller
@RequestMapping("file")
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @Autowired
    public UserRepository userRepo;

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    @GetMapping("/")
    @ResponseBody
    public CustomResponse listUploadedFiles(Model model) throws IOException {
        logger.info("Listing uploaded files");
        model.addAttribute("files", storageService
                .loadAll()
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList()));

//        return "uploadForm";
        return new CustomResponse(true);
    }

    @GetMapping("files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        logger.info("Returning data for file: {}", filename);
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @PostMapping("/{id}")
    @ResponseBody
    public CustomResponse handleFileUpload(@PathVariable int id, @RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        logger.info("Received request for storing file: {}", file.getOriginalFilename());

        String newFileName = LocalDateTime.now().format(dtf);
        String originalFilename = file.getOriginalFilename();
        newFileName += originalFilename.substring(originalFilename.lastIndexOf("."));
        storageService.store(file, newFileName);

        User user = userRepo.findOne(id);
        storageService.delete(user.getImgName());
        user.setImgName(newFileName);
        userRepo.updateUser(user);

//        return "redirect:/file/";
        CustomResponse response = new CustomResponse(true);
        response.addData("newImg", newFileName);
        return response;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
