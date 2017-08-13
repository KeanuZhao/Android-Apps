package com.finalp.keanuzhao.myapplication.Common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by KeanuZhao on 11/04/2017.
 */

public class WebServiceGET {

    private static String IP = "172.20.10.6:8080";


    public static String executeHttpLogin(String username, String password) {

        HttpURLConnection conn = null;
        InputStream is = null;

        try {

            String path = "http://" + IP + "/AndroidServer/LoginServlet";
            path = path + "?username=" + username + "&password=" + password.replace(" ","qazwsx").replace("?","ehbfg");
           // System.out.println(path);
            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Charset", "UTF-8");

            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                return parseInfo(is);
            }

        }catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (conn != null) {
                conn.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public static String executeHttpRegister(String username, String password) {

        HttpURLConnection conn = null;
        InputStream is = null;

        try {

            String path = "http://" + IP + "/AndroidServer/RegisterServlet";
            path = path + "?username=" + username + "&password=" + password;
            // System.out.println(path);
            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Charset", "UTF-8");

            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                return parseInfo(is);
            }

        }catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (conn != null) {
                conn.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }
    public static String executeHttpGetRead() {

        HttpURLConnection conn = null;
        InputStream is = null;

        try {

            String path = "http://" + IP + "/AndroidServer/GetReadServlet";
           // path = path + "?username=" + username + "&read=" + read;
            // System.out.println(path);
            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Charset", "UTF-8");

            if (conn.getResponseCode() == 200) {

                is = conn.getInputStream();
                return parseInfo(is);
            }

        }catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (conn != null) {
                conn.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }
    public static String executeHttpAdminGetRead() {

        HttpURLConnection conn = null;
        InputStream is = null;

        try {

            String path = "http://" + IP + "/AndroidServer/GetAdminReadServlet";
            // path = path + "?username=" + username + "&read=" + read;
            // System.out.println(path);
            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Charset", "UTF-8");

            if (conn.getResponseCode() == 200) {

                is = conn.getInputStream();
                return parseInfo(is);
            }

        }catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (conn != null) {
                conn.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public static String executeHttpNewPost(String id, String title, String date, String content, String writer) {

        HttpURLConnection conn = null;
        InputStream is = null;

        try {

            String path = "http://" + IP + "/AndroidServer/NewPostServlet";
            path = path + "?id=" + id + "&title=" + title.replace(" ","qazwsx").replace("?","ehbfg") + "&date=" + date + "&content=" + content.replace(" ","qazwsx").replace("?","ehbfg") + "&writer=" + writer;
            System.out.println(path);
            URL url = new URL(path);

            //System.out.println(url.toString());
            conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Charset", "UTF-8");

            if (conn.getResponseCode() == 200) {
               // System.out.println("?????????????????");
                is = conn.getInputStream();
                return parseInfo(is);
            }

        }catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (conn != null) {
                conn.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }
    public static String executeHttpPermit(String id) {

        HttpURLConnection conn = null;
        InputStream is = null;

        try {

            String path = "http://" + IP + "/AndroidServer/PermitPostServlet";
            path = path + "?id=" + id;
            // System.out.println(path);
            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Charset", "UTF-8");

            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                return parseInfo(is);
            }

        }catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (conn != null) {
                conn.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }
    public static String executeHttpAddComment(String id, String postid, String date, String writer, String content) {

        HttpURLConnection conn = null;
        InputStream is = null;

        try {

            String path = "http://" + IP + "/AndroidServer/addCommentServlet";


            path = path + "?id=" + id + "&postid=" + postid + "&date=" + date + "&writer=" + writer + "&content=" + content.replace(" ","rfvtgbyhn").replace("?","qazwsxedc");
            //System.out.println(path);
            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Charset", "UTF-8");

            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                return parseInfo(is);
            }

        }catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (conn != null) {
                conn.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }
    public static String executeHttpLoadComments(String id) {

        HttpURLConnection conn = null;
        InputStream is = null;

        try {

            String path = "http://" + IP + "/AndroidServer/loadCommentsServlet";
            path = path + "?id=" + id;
         //   System.out.println(path);
            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Charset", "UTF-8");

            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                return parseInfo(is);
            }

        }catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (conn != null) {
                conn.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }
    public static String executeHttpAddRead(String name, String read) {

        HttpURLConnection conn = null;
        InputStream is = null;

        try {

            String path = "http://" + IP + "/AndroidServer/addReadServlet";
            path = path + "?name=" + name + "&read=" + read;
            //System.out.println(path);
            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Charset", "UTF-8");

            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                return parseInfo(is);
            }

        }catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (conn != null) {
                conn.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    private static String parseInfo(InputStream inStream) throws Exception {
        byte[] data = read(inStream);

        return new String(data, "UTF-8");
    }


    public static byte[] read(InputStream inStream) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        inStream.close();
        return outputStream.toByteArray();
    }
}
