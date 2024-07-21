package org.dromara.ai.service;

import ai.djl.Device;
import ai.djl.ModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;

import org.dromara.ai.domain.tanslator.FaceDetectionTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * An example of inference using a face detection model.
 *
 * <p>See this <a
 * href="https://github.com/deepjavalibrary/djl/blob/master/examples/docs/face_detection.md">doc</a>
 * for information about this example.
 */
public final class RetinaFaceDetection {

    private static final Logger logger = LoggerFactory.getLogger(RetinaFaceDetection.class);

    private RetinaFaceDetection() {
    }

    public static void main(String[] args) throws IOException, ModelException, TranslateException {
        System.out.println(Arrays.toString(Device.gpu().getDevices().toArray()));
        Device.gpu(0).getDeviceType();
//        DetectedObjects detection = RetinaFaceDetection.predict();
//        logger.info("{}", detection);
    }

    public static DetectedObjects predict() throws IOException, ModelException, TranslateException {
        Path facePath = Paths.get("D:\\projects\\java\\RuoYi-Cloud-Plus\\ai-models\\ai-example\\src\\main\\resources\\largest_selfie.jpg");
        Image img = ImageFactory.getInstance().fromFile(facePath);

        double confThresh = 0.85f;
        double nmsThresh = 0.45f;
        double[] variance = {0.1f, 0.2f};
        int topK = 5000;
        int[][] scales = {{16, 32}, {64, 128}, {256, 512}};
        int[] steps = {8, 16, 32};
        FaceDetectionTranslator translator =
            new FaceDetectionTranslator(confThresh, nmsThresh, variance, topK, scales, steps);

        Criteria<Image, DetectedObjects> criteria =
            Criteria.builder()
                .setTypes(Image.class, DetectedObjects.class)
                .optModelUrls("https://resources.djl.ai/test-models/pytorch/retinaface.zip")
                // Load model from local file, e.g:
                .optModelName("retinaface") // specify model file prefix
                .optTranslator(translator)
                .optProgress(new ProgressBar())
                .optEngine("PyTorch") // Use PyTorch engine
                .optDevice(Device.gpu())
                .build();

        try (ZooModel<Image, DetectedObjects> model = criteria.loadModel();
             Predictor<Image, DetectedObjects> predictor = model.newPredictor()) {
            long l = System.currentTimeMillis();
            DetectedObjects detection = null;
            for (int i = 0; i < 1000; i++) {
                detection = predictor.predict(img);
            }
            System.out.println(System.currentTimeMillis() - l);
            saveBoundingBoxImage(img, detection);
            return detection;
        }
    }

    private static void saveBoundingBoxImage(Image img, DetectedObjects detection)
        throws IOException {
        Path outputDir = Paths.get("build/output");
        Files.createDirectories(outputDir);

        img.drawBoundingBoxes(detection);

        Path imagePath = outputDir.resolve("retinaface_detected.png");
        img.save(Files.newOutputStream(imagePath), "png");
        logger.info("Face detection result image has been saved in: {}", imagePath);
    }
}
