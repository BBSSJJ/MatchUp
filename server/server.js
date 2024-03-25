import express from "express";
import multer from "multer";
import cors from "cors";
import fs from "fs";
import path from "path";

const __dirname = path.resolve();
const app = express();
const HOST_BASE_URL = "https://matchup.site/file";
const PORT = 8001;

// Cors 설정
const corsOptions = {
  // origin: `http://${HOST_BASE_URL}:${1234}`,
  origin: "*",
  credentials: true,
};
app.use(cors(corsOptions));

// 저장 형식
const saveForm = function (req, file, cb) {
  const now = new Date().getTime();
  const fileName = path.basename(
    file.originalname,
    path.extname(file.originalname)
  );
  const ext = path.extname(file.originalname);

  cb(null, fileName + "-" + now + ext);
};

// Multer 설정
const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, "uploads"); // 파일이 저장될 폴더
  },
  filename: saveForm,
});

const upload = multer({ storage: storage });

// 정적 파일 서빙 (uploads 폴더에 있는 파일들을 웹에서 접근 가능하게 함)
app.use("/uploads", express.static("uploads"));
app.use("/file", express.static("dist"));

// Server Listening
app.get("/file/", (req, res) => {
  const index = path.join(__dirname, "/index.html");
  res.status(200).send(fs.readdir(index));
});

// 파일목록 보내기
app.get("/file/uploads", (req, res) => {
  const uploadPath = path.join(__dirname, "uploads");
  fs.readdir(uploadPath, (err, files) => {
    if (err) return res.status(500).send("서버 오류");
    return res.json({ files });
  });
});

// 파일 업로드 처리
app.post("/file/uploads", upload.any(), (req, res) => {
  console.log(req);
  // res.status(200).send({ message: "successfully uploaded!", files: req.files });
  res.status(200).send({ fileName: req.files[0].filename });
});

// 파일 삭제 처리
app.delete("/file/uploads/:file", upload.any(), (req, res) => {
  const deletePath = path.join(__dirname, `uploads/${req.params.file}`);
  fs.unlink(deletePath, (err) => {
    if (err) return res.status(500).send("서버 오류");
    return res.status(200).send("파일이 삭제되었습니다..");
  });
});

// 실행여부 콘솔에 찍기
app.listen(PORT, () => {
  console.log(`서버가 ${HOST_BASE_URL} 에서 실행 중입니다.`);
});
