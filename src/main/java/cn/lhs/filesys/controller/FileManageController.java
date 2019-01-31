package cn.lhs.filesys.controller;

import cn.lhs.filesys.entity.FileMsg;
import cn.lhs.filesys.entity.MyFile;
import cn.lhs.filesys.entity.ResponseMsg;
import cn.lhs.filesys.service.FileManageService;
import cn.lhs.filesys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/manage")
public class FileManageController {

    private static final Logger logger = LoggerFactory.getLogger(FileManageController.class);

    @Autowired
    FileManageService fileManageService;

    @Autowired
    UserService userService;

    @GetMapping("/listMyOwnFile")
    public ResponseMsg listMyOwnFile(@RequestParam(required = true,value = "uploaderId")String uploaderId,
                                     @RequestParam(required = false,value = "fileName")String fileName,
                                     @RequestParam(required = false,value = "fileSort")String fileSort,
                                     @RequestParam(required = false,value = "isShared")String isShared,
                                     @RequestParam(required = true,value = "pageIndex")int pageIndex,
                                     @RequestParam(required = true,value = "pageSize")int pageSize){

        logger.info("fileName="+fileName+",fileSort="+fileSort+",isShared="+isShared);
        if(fileName != null && fileName.trim().length()==0){
            fileName = null;
        }
        if(fileSort != null && fileSort.trim().length()==0){
            fileSort = null;
        }
        if (isShared != null && isShared.trim().length()==0){
            isShared = null;
        }
        int count = fileManageService.getMyOwnFileNum(uploaderId, fileName, fileSort, isShared);
        logger.info("总条数="+count);
        if(count==0){
            return new ResponseMsg(1,"暂无数据",0,null);
        }

        List<MyFile> myFileList = fileManageService.getMyOwnFile(uploaderId, fileName, fileSort, isShared, pageIndex, pageSize);
        List<FileMsg> fileMsgList = new ArrayList<>();
        for (int i = 0; i < myFileList.size(); i++) {
            fileMsgList.add(new FileMsg(myFileList.get(i)));
        }
        return new ResponseMsg(1,"成功",count,fileMsgList);
    }

    @GetMapping("/listSharedFile")
    public ResponseMsg searchFile(@RequestParam(required = false,value = "fileName")String fileName,
                                  @RequestParam(required = false,value = "fileSort")String fileSort,
                                  @RequestParam(required = true,value = "pageIndex")int pageIndex,
                                  @RequestParam(required = true,value = "pageSize")int pageSize){

        logger.info("fileName="+fileName+",fileSort="+fileSort);
        if(fileName != null && fileName.trim().length()==0){
            fileName = null;
        }
        if(fileSort != null && fileSort.trim().length()==0){
            fileSort = null;
        }
        int count = fileManageService.getSharedFileNum(fileName, fileSort);
        logger.info("总条数="+count);
        if(count==0){
            return new ResponseMsg(1,"暂无数据",0,null);
        }
        List<MyFile> myFileList = fileManageService.getSharedFile(fileName, fileSort, pageIndex, pageSize);
        List<FileMsg> fileMsgList = new ArrayList<>();
        for (int i = 0; i < myFileList.size(); i++) {
            fileMsgList.add(new FileMsg(myFileList.get(i)));
        }
        return new ResponseMsg(1,"成功",count,fileMsgList);
    }


    @GetMapping("/modifyFileName")
    public ResponseMsg modifyFileName(@RequestParam(required = true,value = "uploaderId")String uploaderId,
                              @RequestParam(required = true,value = "fileUrl")String fileUrl,
                              @RequestParam(required = true,value = "fileName")String fileName){
        logger.info("uploaderId="+uploaderId+",fileUrl="+fileUrl+",fileName"+fileName);
        int code = fileManageService.modifyMyFile(uploaderId, fileUrl, fileName);
        String msg = "";
        if(code == 0){
            msg = "修改失败";
        }else {
            msg = "修改成功";
        }
        return new ResponseMsg(code,msg);
    }

    @GetMapping("/delFile")
    public ResponseMsg delFile(@RequestParam(required = true,value = "uploaderId")String uploaderId,
                               @RequestParam(required = true,value = "fileUrl")String fileUrl){
        logger.info("uploaderId="+uploaderId+",fileUrl="+fileUrl);
        int code = fileManageService.delMyFile(uploaderId, fileUrl);
        boolean flag;
        String msg;
        File file = new File(fileUrl);
        if(file.exists()){
            flag = file.delete();
        }else {
            flag = true;
        }
        if(code == 0 && flag){
            msg = "删除失败";
        }else {
            msg = "删除成功";
        }
        return new ResponseMsg(code,msg);
    }

    @GetMapping("/addDownloadTimes")
    public ResponseMsg addDownloadTimes(@RequestParam(value = "uploaderId")String uploaderId,
                                        @RequestParam(value = "fileId")String fileId){
        logger.info("uploaderId="+uploaderId+",fileId="+fileId);
        int code = fileManageService.addDownloadTimes(uploaderId,fileId);
        String msg;
        if (code == 0){
            msg = "修改失败";
        }else {
            msg = "修改成功";
        }
        return new ResponseMsg(code,msg);
    }


    @GetMapping("/shareFile")
    public ResponseMsg shareFile(@RequestParam(required = true,value = "uploaderId")String uploaderId,
                                 @RequestParam(required = true,value = "fileUrl")String fileUrl,
                                 @RequestParam(required = true,value = "isShared")String isShared){
        logger.info("uploaderId="+uploaderId+",fileUrl="+fileUrl+",isShared="+isShared);
        int code = fileManageService.shareFile(uploaderId, fileUrl, isShared);
        String msg = "";
        if(code == 0){
            msg = "操作失败";
        }else if(code == 1 && isShared.equals("1")){
            msg = "分享成功";
        }else if(code == 1 && isShared.equals("0")){
            msg = "已取消分享";
        }
        return new ResponseMsg(code,msg);
    }

}
