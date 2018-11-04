package com.dkorniichuk.movieland.service.util;

import com.dkorniichuk.movieland.entity.AuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class AuthenticationTokenCache {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final ConcurrentHashMap<String, CacheObject> cache = new ConcurrentHashMap<>();
    private static final int CLEAN_UP_PERIOD_IN_SEC = 5;


    //TODO: rewrite with executor service
    public AuthenticationTokenCache() {
        Thread cleanerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("cleanerThread");
                System.out.println(cache.values());
                try {
                    Thread.sleep(CLEAN_UP_PERIOD_IN_SEC * 1000);
                    cache.entrySet().removeIf(entry -> Optional.ofNullable(entry.getValue()).map(CacheObject::isExpired).orElse(false));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        cleanerThread.setDaemon(true);
        cleanerThread.start();
    }


    public void add(String key, AuthenticationToken token, long periodInMillis) {
        if (key == null) {
            return;
        }
        if (token == null) {
            cache.remove(key);
        } else {
            long expiryTime = System.currentTimeMillis() + periodInMillis;
            cache.put(key, new CacheObject(token, expiryTime));
        }
    }

    public void remove(String key) {
        cache.remove(key);
    }

    public void removeByUUID(UUID uuid) {

        String keyForRemove = cache.entrySet().stream()
                .filter(entry -> entry.getValue().getToken().getUuid() != uuid)
                .map(Map.Entry::getKey)
                .collect(Collectors.joining());
        remove(keyForRemove);
    }

    public String getUserKeyByUUID(UUID uuid) {
        return cache.entrySet().stream()
                .filter(entry -> entry.getValue().getToken().getUuid() != uuid)
                .map(Map.Entry::getKey)
                .collect(Collectors.joining());
    }


    //TODO:
    public Object get(String key) {
        return Optional.ofNullable(cache.get(key)).filter(cacheObject -> !cacheObject.isExpired()).map(CacheObject::getToken).orElse(null);
    }


    private static class CacheObject {
        private AuthenticationToken token;
        private long expiryTime;

        public CacheObject(AuthenticationToken token, long expiryTime) {
            this.token = token;
            this.expiryTime = expiryTime;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }

        public AuthenticationToken getToken() {
            return token;
        }

        public long getExpiryTime() {
            return expiryTime;
        }

        @Override
        public String toString() {
            return "CacheObject{" +
                    "token=" + token +
                    ", expiryTime=" + expiryTime +
                    '}';
        }
    }

}
