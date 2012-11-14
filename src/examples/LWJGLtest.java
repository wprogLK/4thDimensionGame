package examples;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;

public class LWJGLtest {

        public static void main(String[] args) {
        //      ^ Der einzig sinnvolle static für jetzt
                
                // Diese Klasse instanziieren sodass es nicht als static läuft
                // Als static DARF DAS PROGRAMM NICHT laufen
                LWJGLtest lwjgl = new LWJGLtest();
                
                // Instanz laufen lassen
                lwjgl.start();
                
        }
        
        // Nicht statische Methode/Funktion
        public void start() {
                try {
                
                        // Versuche Fenster auf 800x600 zu stellen
                        Display.setDisplayMode(new DisplayMode(800,600));
                    
                        // Versuche Fenster zu erzeugen
                        Display.create();
                    
                // Aufpassen wenn was schief geht
                } catch (LWJGLException e) {
                        e.printStackTrace();
                    
                        // Wenn was an dieser Stelle schon schief geht ist alles vorbei :(
                        System.exit(0);
                }
        
                // OpenGL Initialisierung
                
                // Depth Test aktivieren da wir sonst in 3D merkwürdige Dinge sehen
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                
                // OpenGL sagen, dass unser fenster
                // an Koordinaten (0,0) ihr Ursprung hat und
                // 800 Pixel breit und 600 Pixel hoch ist
                GL11.glViewport(0, 0, 800, 600);
                
                // Wir wollen jetzt mit dem Projection Matrix arbeiten
                GL11.glMatrixMode(GL11.GL_PROJECTION);
                
                // Standard Matrix laden
                GL11.glLoadIdentity();
                
                // OpenGL sagen, dass wir eine 90 Grad (45 Grad in beide Richtungen)
                // Blickwinkel haben wollen und, dass unser Seitenverhältnis 800/600
                // beträgt. Wir wollen nur Sachen zwischen 0.1f und 1000.0f entfernung malen.
                GLU.gluPerspective(45.0f, 800.0f/600.0f, 0.1f, 1000.0f);
                
                // Wir wollen jetzt wieder mit dem Modelview Matrix arbeiten
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                
                // Zeitpunkt des letzten Frames
                long lastTime = Sys.getTime() * 1000 / Sys.getTimerResolution();
                
                // Winkel um den wir ständig rotieren
                float angle = 0.0f;
                
                // Solange der Benutzer das Programm nicht beenden will
                while (!Display.isCloseRequested()) {
                
                        // Programmlogik hier machen
                        
                        // Wir löschen in jedem Frame in jedem Pixel die Farbe-bits
                        // und Tiefen-Reihenfolge-bits sodass wir auf ein frisches Bildschirm malen können
                        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
                        
                        // Wenn man eine bestimmte Hintergrundfarbe will macht man das. Format RGBA
                        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                        
                        // Standard Matrix laden
                        GL11.glLoadIdentity();
                        
                        // Bewege 10 nach unten und 60 nach hinten sodass wir was sehen
                        GL11.glTranslatef(0.0f, -10.0f, -60.0f);
                        
                        // Unser aktuelle Zeit in ms
                        long currentTime = Sys.getTime() * 1000 / Sys.getTimerResolution();
                        
                        // Winkel zwischen 0 und 360 wachsen lassen
                        angle += ((currentTime - lastTime) / 10.0f) % 360;
                        
                        // Zeitpunkt des letzten Frames ist jetzt
                        lastTime = currentTime;
                        
                        // Rotiere um die Y-Achse um angle Grad
                        GL11.glRotatef(angle, 0.0f, 1.0f, 0.0f);
                    
                        // Farbe auswählen womit wir alle zukünftigen Ecken malen
                        // Farben sind entweder in RGB oder RGBA format A = Alpha = Durchsichtigkeit
                        // Bei gl______f handelt es sich um float Werte zwischen 0 und 1.0
                        // Alles andere z.B. gl______ub sollte man in Java vermeiden
                        GL11.glColor3f(0.0f,1.0f,0.0f);
                        
                        // Beginne mit einem Viereck
                        GL11.glBegin(GL11.GL_QUADS);
                    
                        // Erste Ecke festlegen
                        GL11.glVertex3f(-5,-5,5);
                        
                        // Zweite Ecke festlegen
                        GL11.glVertex3f(5,-5,5);
                        
                        // Zwischendurch farbe wechseln
                        GL11.glColor3f(0.0f,0.0f,1.0f);
                        
                        // Dritte Ecke festlegen
                        GL11.glVertex3f(5,5,5);
                        
                        // Vierte Ecke festlegen
                        GL11.glVertex3f(-5,5,5);
                        
                        // Nach vier Ecken sind wir mit einem Viereck fertig... lol
                        GL11.glEnd();
                        
                        // Zweite Seite
                        GL11.glBegin(GL11.GL_QUADS);
                        GL11.glColor3f(1.0f,0.0f,1.0f);
                        GL11.glVertex3f(-5,-5,-5);
                        GL11.glVertex3f(-5,5,-5);
                        GL11.glColor3f(1.0f,1.0f,0.0f);
                        GL11.glVertex3f(5,5,-5);
                        GL11.glVertex3f(5,-5,-5);
                        GL11.glEnd();
                        
                        // Dritte Seite
                        GL11.glBegin(GL11.GL_QUADS);
                        GL11.glColor3f(1.0f,0.0f,5.0f);
                        GL11.glVertex3f(5,-5,5);
                        GL11.glVertex3f(5,5,5);
                        GL11.glColor3f(1.0f,1.0f,1.0f);
                        GL11.glVertex3f(5,5,-5);
                        GL11.glVertex3f(5,-5,-5);
                        GL11.glEnd();
                        
                        // Vierte Seite
                        GL11.glBegin(GL11.GL_QUADS);
                        GL11.glColor3f(1.0f,0.5f,0.0f);
                        GL11.glVertex3f(-5,-5,-5);
                        GL11.glVertex3f(-5,5,-5);
                        GL11.glColor3f(1.0f,0.5f,1.0f);
                        GL11.glVertex3f(-5,5,5);
                        GL11.glVertex3f(-5,-5,5);
                        GL11.glEnd();
                        
                        // Fünfte Seite
                        GL11.glBegin(GL11.GL_QUADS);
                        GL11.glColor3f(0.5f,0.5f,1.0f);
                        GL11.glVertex3f(-5,5,-5);
                        GL11.glVertex3f(5,5,-5);
                        GL11.glColor3f(0.0f,5.0f,1.0f);
                        GL11.glVertex3f(5,5,5);
                        GL11.glVertex3f(-5,5,5);
                        GL11.glEnd();
                        
                        // Sechste Seite
                        GL11.glBegin(GL11.GL_QUADS);
                        GL11.glColor3f(0.5f,0.5f,0.5f);
                        GL11.glVertex3f(-5,-5,5);
                        GL11.glVertex3f(5,-5,5);
                        GL11.glColor3f(0.5f,0.0f,1.0f);
                        GL11.glVertex3f(5,-5,-5);
                        GL11.glVertex3f(-5,-5,-5);
                        GL11.glEnd();
                        
                        // Unser neuer Frame den Benutzer präsentieren
                        Display.update();
                }
                
                // Wir sind fertig. Weg mit dem Fenster.
                Display.destroy();
                
        }
}