package com.jessechiu.android.ontepad;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class ChartsActivity extends AppCompatActivity {

    private LineChartView mchart;
    // 日期和金额 map 数据
    private Map<String,Integer> dateMap = new TreeMap();
    private LineChartData lineData;// 线图数据

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_view);

        mchart = (LineChartView) findViewById(R.id.chart);

        // 获取主窗口传递过来数据
        List<CostBean> alldata = (List<CostBean>) getIntent().getSerializableExtra("cost_list");
        // 格式化带显示数据
        formatData(alldata);
        // 生成图表数据
        generateChartData();
    }

    // 格式化带显示数据
    private void formatData(List<CostBean> data) {
        if(data != null){
            for(int i=0;i< data.size();i++){
                CostBean bean = data.get(i);
                String costDate = bean.costDate;
                // 金额转为整型
                int costMoney =  Integer.parseInt(bean.costMoney);
                Log.d("notepad","---"+costDate + " " + costMoney);
                if(!dateMap.containsKey(costDate)){
                    dateMap.put(costDate,costMoney);
                }else{
                    // 如果已经存在，则累加金额值
                    dateMap.put(costDate,costMoney+(dateMap.get(costDate)));
                }
            }
        }

    }

    // 生成图表数据
    private void generateChartData() {
        List<Line> lines = new ArrayList<>();
        List<PointValue> values = new ArrayList<PointValue>();

        int index = 0;
        // 生成金额-坐标数据
        for(Integer value: dateMap.values()){
            values.add(new PointValue(index,value));
            index++;
        }

        Line line = new Line(values);
        line.setColor(ChartUtils.COLOR_BLUE);
        line.setShape(ValueShape.CIRCLE);
        line.setPointColor(ChartUtils.COLOR_RED);

        lines.add(line);
        lineData = new LineChartData(lines);
        mchart.setLineChartData(lineData);
    }
}
