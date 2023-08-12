package persistence_adapter

import (
	"errors"
	"os"

	"github.com/redis/go-redis/v9"
)

func NewRedisClient() (*redis.Client, error) {
	host := os.Getenv("BUSKING_GPS_REDIS_HOST")
	if len(host) == 0 {
		return nil, errors.New("NewRedisClient(): BUSKING_GPS_REDIS_HOST is not present")
	}

	port := os.Getenv("BUSKING_GPS_REDIS_PORT")
	if len(port) == 0 {
		return nil, errors.New("NewRedisClient(): BUSKING_GPS_REDIS_PORT is not present")
	}

	return redis.NewClient(&redis.Options{
		Addr:     host + ":" + port,
		Password: "",
		DB:       0,
	}), nil
}
