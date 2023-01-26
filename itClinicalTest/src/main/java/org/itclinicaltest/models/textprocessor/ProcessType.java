package org.itclinicaltest.models.textprocessor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum ProcessType {
    UPPERCASE_NUMBERS_SPECIAL_CHAR("special"),
    ONLY_UPPERCASE("upper");

    private static final Map<String, ProcessType> BY_PROCESS_NAME;

    static {
        Map<String, ProcessType> map = new HashMap<>();
        for (ProcessType e : values()) {
            map.put(e.getProcessName(), e);
        }
        BY_PROCESS_NAME = Collections.unmodifiableMap(map);
    }

    private final String processName;

    ProcessType(String upper) {
        this.processName = upper;
    }

    public String getProcessName() {
        return processName;
    }

    public static Optional<ProcessType> getProcessTypeProcessName(String processName) {

        if (!BY_PROCESS_NAME.containsKey(processName))
            return Optional.empty();

        return Optional.of(BY_PROCESS_NAME.get(processName));
    }
}
