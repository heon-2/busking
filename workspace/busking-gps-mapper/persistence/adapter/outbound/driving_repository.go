package persistence_outbound

import (
	"context"
	"strconv"

	"busking.org/gps-mapper/domain/application/model"
	"github.com/redis/go-redis/v9"
)

type DrivingRepository struct {
	Db *redis.Client
}

func (repo *DrivingRepository) BeginDriving(busId model.BusId) {
	repo.Db.SAdd(context.Background(), "company:"+strconv.FormatInt(busId.CompanyId, 10), "bus:"+busId.String())
}

func (repo *DrivingRepository) EndDriving(busId model.BusId) {
	repo.Db.SRem(context.Background(), "company:"+strconv.FormatInt(busId.CompanyId, 10), "bus:"+busId.String())
}

func NewDrivingRepository(db *redis.Client) *DrivingRepository {
	return &DrivingRepository{Db: db}
}
