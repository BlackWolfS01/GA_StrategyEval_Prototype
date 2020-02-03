package sample;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.PointLight;
import javafx.scene.PerspectiveCamera;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.MeshView;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        TriangleMesh pyramidMesh = new TriangleMesh();
        float h = 150.0f;
        float s = 300.0f;
        pyramidMesh.getPoints().addAll(
                  0,    0,    0,            // Point 0 - Top
                  0,    h,    -s/2,         // Point 1 - Front
                  -s/2, h,    0,            // Point 2 - Left
                  s/2,  h,    0,            // Point 3 - Back
                  0,    h,    s/2
        );
        pyramidMesh.getFaces().addAll(
                  0,0,  2,0,  1,0,          // Front left face
                  0,0,  1,0,  3,0,          // Front right face
                  0,0,  3,0,  4,0,          // Back right face
                  0,0,  4,0,  2,0,          // Back left face
                  4,0,  1,0,  2,0,          // Bottom rear face
                  4,0,  3,0,  1,0           // Bottom front face
        );

        MeshView pyramid = new MeshView(pyramidMesh);
        pyramid.setDrawMode( DrawMode.FILL);
        pyramid.setTranslateX(200);
        pyramid.setTranslateY(100);
        pyramid.setTranslateZ(200);


        MeshView meshView = this.createMeshView();
        meshView.setTranslateX(250);
        meshView.setTranslateY(100);
        meshView.setTranslateZ(400);

        // Scale the Meshview to make it look bigger
        meshView.setScaleX(10.0);
        meshView.setScaleY(10.0);
        meshView.setScaleZ(10.0);

        // Create a Camera to view the 3D Shapes
        PerspectiveCamera camera = new PerspectiveCamera(false);
        camera.setTranslateX(100);
        camera.setTranslateY(-50);
        camera.setTranslateZ(300);

        // Add a Rotation Animation to the Camera
        RotateTransition rt = new RotateTransition( Duration.seconds(2), camera);
        rt.setCycleCount( Animation.INDEFINITE);
        rt.setFromAngle(-30);
        rt.setToAngle(30);
        rt.setAutoReverse(true);
        rt.setAxis(Rotate.Y_AXIS);
        rt.play();

        // Create the red Front Light
        PointLight redLight = new PointLight();
        redLight.setColor(Color.RED);
        redLight.setTranslateX(250);
        redLight.setTranslateY(150);
        redLight.setTranslateZ(300);

        // Create the green Back Light
        PointLight greenLight = new PointLight();
        greenLight.setColor(Color.GREEN);
        greenLight.setTranslateX(200);
        greenLight.setTranslateY(150);
        greenLight.setTranslateZ(450);

        // Add the Shapes and the Light to the Group
        Group root = new Group(meshView, redLight, greenLight);
        // Rotate the triangle with its lights to 90 degrees
        root.setRotationAxis( Rotate.Y_AXIS);
        root.setRotate(90);
        root.getChildren().add(pyramid);

        // Create a Scene with depth buffer enabled
        Scene scene = new Scene(root, 400, 300, true);
        // Add the Camera to the Scene
        scene.setCamera(camera);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public MeshView createMeshView()
    {
        float[] points =
                  {
                            50, 10, 0,
                            45, 10, 0,
                            55, 10, 0
                  };

        float[] texCoords =
                  {
                            0.5f, 0.5f,
                            1.0f, 1.0f,
                            1.0f, 1.0f
                  };

        int[] faces =
                  {
                            0, 0, 2, 2, 1, 1,
                            0, 0, 1, 1, 2, 2,
                            0, 0, 2, 2, 1, 1
                  };

        // Create a TriangleMesh
        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(points);
        mesh.getTexCoords().addAll(texCoords);
        mesh.getFaces().addAll(faces);

        // Create a NeshView
        MeshView meshView = new MeshView();
        meshView.setMesh(mesh);

        return meshView;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
