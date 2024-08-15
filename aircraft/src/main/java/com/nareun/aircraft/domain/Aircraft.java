package com.nareun.aircraft.domain;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// * 상응하는 멤버 변수 없으면 Jackson이 이를 무시
@JsonIgnoreProperties(ignoreUnknown = true)
@RedisHash
public class Aircraft {

    @Id
    private Long id;

    private String callsign, squawk, reg, flightno, route, type, category;

    private int altitude, heading, speed;

    @JsonProperty("vert_rate")
    private int vertRate;

    @JsonProperty("selected_altitude")
    private int selectedAltitude;

    private double lat, lon, barometer;

    @JsonProperty("polar_distance")
    private double polarDistance;

    @JsonProperty("polar_bearing")
    private double polarBearing;

    @JsonProperty("is_adsb")
    private boolean isASDB;

    @JsonProperty("is_on_ground")
    private boolean isOnGround;

    @JsonProperty("last_seen_time")
    private Instant lastSeenTime;

    @JsonProperty("pos_update_time")
    private Instant posUpdateTime;

    @JsonProperty("bds40_seen_time")
    private Instant bds40SeenTime;

    //! Spring Data의 변환기가 복잡한 타입을 쉽게 처리하기 때문에 필요가 없어짐.
    // public String getLastSeenTime() {
    //     return lastSeenTime.toString();
    // }
    
    // public void setLastSeenTime(String lastSeenTime) {
    //     if (null != lastSeenTime) {
    //         // * Instant : UTC기준시(1970년 1월1일 0시 0분 0초)를 0으로 잡고 Instant.EPOCH -> 여기로 부터 경과시간을
    //         // 양수 또는 음수로(millisecond) */
    //         this.lastSeenTime = Instant.parse(lastSeenTime);
    //     } else {
    //         this.lastSeenTime = Instant.ofEpochSecond(0);
    //     }
    // }

    // //* String이 값을 가장 잘 직렬화 시킴.w
    // public String getPosUpdateTime() {
    //     return posUpdateTime.toString();
    // }

    // public void setPosUpdateTime(String posUpdateTime) {
    //     if (null != posUpdateTime) {
    //           this.posUpdateTime = Instant.parse(posUpdateTime);
    //     } else {
    //         this.posUpdateTime = Instant.ofEpochSecond(0);
    //     }
    // }

    // public String getBds40SeenTime() {
    //     return bds40SeenTime.toString();
    // }

    // public void setBds40SeenTime(String bds40SeenTime) {
    //     if (null != bds40SeenTime) {
    //         this.bds40SeenTime = Instant.parse(bds40SeenTime);
    //     } else {
    //         this.bds40SeenTime = Instant.ofEpochSecond(0);
    //     }
    // }
}
