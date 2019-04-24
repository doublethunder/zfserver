package com.dt.miniapp.model.tuting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author chenlei
 * @date 2019-04-24
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TuringResponse {
    private Intent intent;
    private List<Result> results;
}
