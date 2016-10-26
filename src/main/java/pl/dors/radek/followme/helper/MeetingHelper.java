package pl.dors.radek.followme.helper;

import pl.dors.radek.followme.model.Meeting;

/**
 * Created by rdors on 2016-10-26.
 */
public class MeetingHelper {

    public static void copyWithoutIdAndCollections(Meeting source, Meeting target) {
        target.setName(source.getName());
    }
}
