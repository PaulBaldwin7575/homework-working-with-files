package com.sample.drivers.screen.driver;

import com.sample.drivers.entity.Document;
import io.jmix.core.FileRef;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.Image;
import io.jmix.ui.component.LinkButton;
import io.jmix.ui.component.impl.LinkButtonImpl;
import io.jmix.ui.download.Downloader;
import io.jmix.ui.screen.*;
import com.sample.drivers.entity.Driver;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Driver.edit")
@UiDescriptor("driver-edit.xml")
@EditedEntityContainer("driverDc")
public class DriverEdit extends StandardEditor<Driver> {
    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    private Image<byte[]> photoPreview;
    @Autowired
    private Downloader downloader;

    @Subscribe("photoUploadField")
    public void onPhotoUploadFieldValueChange(HasValue.ValueChangeEvent<byte[]> event) {
        photoPreview.setVisible(event.getValue() != null);

    }

    @Install(to = "documentsTable.downloadFileColumn", subject = "columnGenerator")
    private Component documentsTableDownloadFileColumnColumnGenerator(Document document) {
        if (document.getFile() != null) {
            FileRef file = document.getFile();
            LinkButton button = uiComponents.create(LinkButton.class);
            button.setCaption(file.getFileName());
            button.addClickListener(e -> downloader.download(file));
            return button;
        }
        return null;
    }
}