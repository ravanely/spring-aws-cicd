package cm.nsi.saarngan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * created by : Ravanely
 * create at : 06/04/2022, 10:14, mer.
 * saar-ngan
 **/
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String dirName = "/user-photos";
        Path userPhotosDir = Paths.get(dirName);
        String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:" + userPhotosPath + "/");


        String categoryDirName = "/category-images";
        Path categoryPhotosDir = Paths.get(categoryDirName);
        String categoryPhotosPath = categoryPhotosDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + categoryDirName + "/**").addResourceLocations("file:" + categoryPhotosPath + "/");
    }
}
