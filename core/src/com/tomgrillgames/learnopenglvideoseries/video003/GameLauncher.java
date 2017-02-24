package com.tomgrillgames.learnopenglvideoseries.video003;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GameLauncher {

    private long window;

    public static void main(String[] args) {
        new GameLauncher().start();
    }

    private void start() {
        System.out.println("LWJGL Version " + Version.getVersion());

        boolean contextInitiated = glfwInit();
        if (!contextInitiated) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        window = glfwCreateWindow(1200, 800, "openGL Window", NULL, NULL);

        if (window == NULL) {
            throw new RuntimeException("Windows creation failed.");
        }


        glfwSetKeyCallback(window, new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {

                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                    glfwSetWindowShouldClose(window, true);
                }

            }
        });


        glfwMakeContextCurrent(window);

        glfwSwapInterval(GLFW_FALSE);

        glfwShowWindow(window);

        GL.createCapabilities();


        /* ############################################################ */

        float[] vertices = {
                // x  y  z
                0, .5f, 0,       // top center
                .5f, -.5f, 0,        // bottom right
                -.5f, -.5f, 0          // bottom left
        };


        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);


        /* ############################################################ */


        while (!glfwWindowShouldClose(window)) {

            glClearColor(1f, 0, 0, 1f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


            glBindVertexArray(vao);
            glDrawArrays(GL_TRIANGLES, 0, 3);
            glBindVertexArray(0);



            glfwSwapBuffers(window);

            glfwPollEvents();

        }

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();

    }
}
